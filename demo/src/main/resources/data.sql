INSERT INTO categories(name, customer_id) VALUES('仕事', 1);
INSERT INTO categories(name, customer_id) VALUES('趣味', 1);
INSERT INTO categories(name, customer_id) VALUES('生活', 1);
INSERT INTO categories(name, customer_id) VALUES('その他', 1);

INSERT INTO categories(name, customer_id) VALUES('仕事', 2);
INSERT INTO categories(name, customer_id) VALUES('趣味', 2);
INSERT INTO categories(name, customer_id) VALUES('生活', 2);
INSERT INTO categories(name, customer_id) VALUES('その他', 2);

INSERT INTO customers(name, user_name, email, password) VALUES('田中太郎', 'Taro', 'tanaka@aaa.com', 'himitu');
INSERT INTO customers(name, user_name, email, password) VALUES('鈴木一郎', 'イチロー', 'suzuki@aaa.com', 'himitu');

INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety) VALUES(5, 2, 'Springフレームワークの教科書を持ってくる', '2023-05-15', 1, 0, 3);
INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety) VALUES(1, 1, '3分スピーチを考える', '2023-05-16', 1, 0, 3);
INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety, hz) VALUES(1, 1, '日報を提出する', '2023-05-15', 1, 1, 2, 1);

INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety, hz) VALUES(6, 2, 'マンガを買う', '2023-05-27', 3, 0, 2, 30);
INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety) VALUES(2, 1, 'ゲームをダウンロードする', '2023-05-20', 2, 0, 3);
INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety) VALUES(6, 2, 'ショッピングに出かける', '2023-05-21', 2, 0, 3);

INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety, hz) VALUES(7, 2, '買い物に行く', '2023-05-21', 1, 0, 2, 7);
INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety, hz) VALUES(3, 1, '光熱費の支払い', '2023-05-15', 1, 0, 2, 30);

INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety) VALUES(8, 2, 'シャンプーを買ってくる', '2023-05-30', 2, 0, 3);
INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety) VALUES(4, 1, '母親にプレゼントを買う', '2023-05-14', 1, 1, 3);
INSERT INTO tasks(category_id, customer_id, name, deadline, priority, state, variety) VALUES(8, 2, '部屋の掃除をする', '2023-05-28', 3, 0, 3);