-- 各種テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS customers;

-- カテゴリーテーブル
CREATE TABLE categories
(
id SERIAL PRIMARY KEY,
name TEXT,
customer_id INTEGER
);
-- タスクテーブル
CREATE TABLE tasks
(
id SERIAL PRIMARY KEY,
category_id INTEGER,
customer_id INTEGER,
name TEXT,
deadline DATE,
priority INTEGER,
state INTEGER,
flag INTEGER,
detail TEXT,
variety INTEGER,
hz INTEGER
);
-- 顧客テーブル
CREATE TABLE customers
(
id SERIAL PRIMARY KEY,
name TEXT,
user_name TEXT,
email TEXT,
password TEXT
);