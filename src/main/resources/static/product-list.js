window.toggleLike = async function (button) {
    const productId = button.getAttribute("data-product-id");
    const userIdElement = document.getElementById('currentUserId');
    const userId = userIdElement ? userIdElement.value : null;

    if (!productId) {
        console.error("Product ID is missing!");
        return;
    }

    const likeCountElement = document.getElementById(`like-count-${productId}`);
    if (!likeCountElement) {
        console.error(`Like count element for product ID ${productId} is missing!`);
        return;
    }

    if (!userId || userId === "null") {
        alert("로그인해야 가능합니다.");
        return;
    }

    try {
        const response = await fetch(`/api/like/${productId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId: userId })
        });

        if (response.ok) {
            const result = await response.json();
            const currentCount = parseInt(likeCountElement.textContent, 10);

            if (result.liked) {
                likeCountElement.textContent = currentCount + 1;
                button.textContent = "Unlike";
            } else {
                likeCountElement.textContent = Math.max(0, currentCount - 1);
                button.textContent = "Like";
            }
        } else {
            alert("좋아요 처리 중 오류가 발생했습니다.");
        }
    } catch (error) {
        console.error("좋아요 요청 중 오류:", error);
        alert("좋아요 처리 중 오류가 발생했습니다.");
    }
};

