const mysql = require('mysql');

// MySQL 데이터베이스 연결 설정
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'your_password', // 비밀번호 입력
    database: 'your_database' // 데이터베이스 이름 입력
});

// 데이터베이스 연결
db.connect((err) => {
    if (err) {
        console.error('MySQL 연결 실패:', err);
        return;
    }
    console.log('MySQL에 연결되었습니다.');
});

// 사용자 ID 설정
const userId = 1; // 동적으로 사용자 ID를 입력할 수 있음

// 구매 정보 가져오기
const getPurchaseInfo = (userId) => {
    const purchaseQuery = `
        SELECT 
            COUNT(*) AS totalPurchases,
            SUM(price) AS totalPurchaseAmount
        FROM 
            Orders
        WHERE 
            user_id = ?;
    `;

    db.query(purchaseQuery, [userId], (err, results) => {
        if (err) {
            console.error('구매 정보 쿼리 실패:', err);
            return;
        }
        console.log('구매 정보:', results[0]);
    });
};

// 판매 정보 가져오기
const getSalesInfo = (userId) => {
    const salesQuery = `
        SELECT 
            COUNT(*) AS totalSales,
            SUM(total_price) AS totalSalesAmount
        FROM 
            Sales_Data
        WHERE 
            user_id = ?;
    `;

    db.query(salesQuery, [userId], (err, results) => {
        if (err) {
            console.error('판매 정보 쿼리 실패:', err);
            return;
        }
        console.log('판매 정보:', results[0]);
    });
};

// 정보 출력
getPurchaseInfo(userId);
getSalesInfo(userId);

// 데이터베이스 연결 종료
db.end((err) => {
    if (err) {
        console.error('MySQL 연결 종료 실패:', err);
        return;
    }
    console.log('MySQL 연결 종료');
});
