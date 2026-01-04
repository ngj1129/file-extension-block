const API_BASE = "/api/extensions";

async function request(url, options = {}) {
    const headers = options.headers ? { ...options.headers } : {};

    if (options.body && !(options.body instanceof FormData)) {
        headers["Content-Type"] = "application/json";
    }

    const res = await fetch(url, { ...options, headers });

    if (res.ok) return res;

    let msg = `요청 실패 (${res.status})`;
    const ct = res.headers.get("content-type") || "";
    try {
        if (ct.includes("application/json")) {
            const data = await res.json();
            msg = data.message || data.error || msg;
        } else {
            const text = await res.text();
            if (text) msg = text;
        }
    } catch (_) {}

    throw new Error(msg);
}

function normalizeExt(ext) {
    return ext.trim().toLowerCase();
}

/* ---------- 고정 확장자 ---------- */
async function updateFixed(ext, blocked) {
    await request(`${API_BASE}/fixed/${encodeURIComponent(ext)}`, {
        method: "PATCH",
        body: JSON.stringify({ blocked }),
    });
}

/* ---------- 커스텀 확장자 ---------- */
async function refreshCustomList() {
    const res = await request(`${API_BASE}/custom`, { method: "GET" });
    const data = await res.json();

    const listEl = document.getElementById("customList");
    listEl.innerHTML = "";

    data.customExtensions.forEach((ext) => {
        listEl.appendChild(makeTag(ext));
    });

    document.getElementById("customCount").textContent = String(data.customExtensions.length);
}

function makeTag(ext) {
    const tag = document.createElement("div");
    tag.className = "tag";
    tag.dataset.ext = ext;

    const span = document.createElement("span");
    span.className = "tag-text";
    span.textContent = ext;

    const btn = document.createElement("button");
    btn.type = "button";
    btn.className = "tag-del";
    btn.textContent = "×";
    btn.setAttribute("aria-label", "delete");

    tag.appendChild(span);
    tag.appendChild(btn);
    return tag;
}

async function addCustom(ext) {
    await request(`${API_BASE}/custom`, {
        method: "POST",
        body: JSON.stringify({ ext }),
    });
    await refreshCustomList();
}

async function deleteCustom(ext) {
    await request(`${API_BASE}/custom/${encodeURIComponent(ext)}`, {
        method: "DELETE",
    });
    await refreshCustomList();
}

async function uploadFile(file) {
    const form = new FormData();
    form.append("file", file);

    const headers = {};

    const res = await fetch("/api/block-test/upload", {
        method: "POST",
        body: form,
        headers,
    });

    if (res.ok) return;

    let msg = `업로드 실패 (${res.status})`;
    const ct = res.headers.get("content-type") || "";
    try {
        if (ct.includes("application/json")) {
            const data = await res.json();
            msg = data.message || msg;
        } else {
            const text = await res.text();
            if (text) msg = text;
        }
    } catch (_) {}

    throw new Error(msg);
}

/* ---------- 이벤트 바인딩 ---------- */
document.addEventListener("DOMContentLoaded", () => {
    // 고정 확장자 체크박스
    document.querySelectorAll(".fixed-checkbox").forEach((cb) => {
        cb.addEventListener("change", async (e) => {
            const el = e.target;
            const ext = el.dataset.ext;
            const blocked = el.checked;

            el.disabled = true;
            try {
                await updateFixed(ext, blocked);
            } catch (err) {
                alert(err.message);
                el.checked = !blocked;
            } finally {
                el.disabled = false;
            }
        });
    });

    // 커스텀 추가
    const input = document.getElementById("customInput");
    const addBtn = document.getElementById("customAddBtn");

    async function onAdd() {
        const raw = input.value;
        const ext = normalizeExt(raw);
        if (!ext) {
            alert("확장자를 입력하세요.");
            return;
        }
        try {
            await addCustom(ext);
            input.value = "";
            input.focus();
        } catch (err) {
            alert(err.message);
        }
    }

    addBtn.addEventListener("click", onAdd);
    input.addEventListener("keydown", (e) => {
        if (e.key === "Enter") onAdd();
    });

    // 커스텀 삭제 (이벤트 위임)
    const listEl = document.getElementById("customList");
    listEl.addEventListener("click", async (e) => {
        const btn = e.target.closest(".tag-del");
        if (!btn) return;

        const tag = btn.closest(".tag");
        const ext = tag?.dataset?.ext;
        if (!ext) return;

        try {
            await deleteCustom(ext);
        } catch (err) {
            alert(err.message);
        }
    });

    // 업로드 테스트
    const fileInput = document.getElementById("uploadFile");
    const uploadBtn = document.getElementById("uploadBtn");
    const uploadResult = document.getElementById("uploadResult");

    uploadBtn.addEventListener("click", async () => {
        const file = fileInput.files?.[0];
        if (!file) {
            alert("파일을 선택하세요.");
            return;
        }

        uploadBtn.disabled = true;
        uploadResult.textContent = "업로드 중...";

        try {
            await uploadFile(file);
            uploadResult.textContent = `업로드 허용 (확장자 차단 아님): ${file.name}`;
        } catch (err) {
            uploadResult.textContent = `${err.message}`;
        } finally {
            uploadBtn.disabled = false;
        }
    });
});
