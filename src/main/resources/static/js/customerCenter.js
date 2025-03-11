document.addEventListener("DOMContentLoaded", () => {
    const noticeList = document.getElementById("noticeList");
    const searchInput = document.getElementById("search");

    function fetchNotices(type = "all", keyword = "") {
        let url = `/customerCenter/filter?type=${type}`;
        if (keyword) {
            url += `&keyword=${encodeURIComponent(keyword)}`;
        }

        fetch(url)
            .then(response => response.json())
            .then(data => {
                noticeList.innerHTML = "";
                data.forEach(notice => {
                    const li = document.createElement("li");
                    li.textContent = notice.title;
                    li.addEventListener("click", () => {
                        window.location.href = `/customerCenter/${notice.noticeEventId}`;
                    });
                    noticeList.appendChild(li);
                });
            });
    }

    searchInput.addEventListener("input", () => {
        fetchNotices("all", searchInput.value);
    });

    window.filterNotices = fetchNotices;
    fetchNotices();
});
