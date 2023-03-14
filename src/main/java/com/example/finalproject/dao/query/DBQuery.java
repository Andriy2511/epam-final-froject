package com.example.finalproject.dao.query;

/**
 * DBQuery interface contains commands for database
 */
public interface DBQuery {

    //Roles
    String SELECT_ALL_ROLES = "SELECT * FROM roles";
    String SELECT_ROLE_ID_BY_NAME = "SELECT id FROM roles WHERE name = ?";
    String SELECT_ROLE_ADMIN_ID = "SELECT id FROM roles WHERE name = 'admin'";
    String SELECT_ROLE_USER_ID = "SELECT id FROM roles WHERE name = 'user'";
    String INSERT_ROLE = "INSERT INTO roles (name) VALUES (?)";
    String UPDATE_ROLE_BY_ID = "UPDATE roles SET name = ? WHERE id = (?)";
    String INSERT_ROLE_BY_NAME = "UPDATE roles SET name = ? WHERE name = (?)";
    String DELETE_ROLE = "DELETE FROM roles WHERE name = ?";

    //Users
    String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = (?)";
    String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = (?)";
    String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? and password = ?";
    String SELECT_USER_ID_BY_LOGIN_AND_PASSWORD = "SELECT id FROM users WHERE login = ? and password = ?";
    String SELECT_ALL_FROM_USERS = "SELECT * FROM users";
    String SELECT_LIMIT_USERS = "SELECT * FROM users WHERE roles_id=? LIMIT ?, ?";
    String SELECT_LIMIT_USERS_BY_BLOCKED_STATUS = "SELECT * FROM users WHERE roles_id=? AND status_blocked=? LIMIT ?, ?";
    String SELECT_NUMBER_OF_USERS = "SELECT COUNT(*) FROM users WHERE roles_id = ?";
    String SELECT_NUMBER_OF_USERS_BY_BLOCKED_STATUS = "SELECT COUNT(*) FROM users WHERE roles_id = ? AND status_blocked=?";
    String SELECT_TOTAL_NUMBER_OF_USERS = "SELECT COUNT(*) FROM users";
    String SELECT_UNBLOCKED_USERS = "SELECT * FROM users WHERE status_blocked='0'";
    String SELECT_BLOCKED_USERS = "SELECT * FROM users WHERE status_blocked='1'";
    String SELECT_USER_ID_BY_LOGIN = "SELECT id FROM users WHERE login = ?";
    String INSERT_USER = "INSERT INTO users (name, surname, login, password, email, roles_id) VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_USER = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?, email= ? WHERE id = ?";
    String UPDATE_USER_NAME = "UPDATE users SET name=? WHERE id = ?";
    String UPDATE_USER_SURNAME = "UPDATE users SET surname=? WHERE id = ?";
    String UPDATE_USER_LOGIN = "UPDATE users SET login=? WHERE id = ?";
    String UPDATE_USER_PASSWORD = "UPDATE users SET password=? WHERE id = ?";
    String UPDATE_USER_EMAIL = "UPDATE users SET email=? WHERE id = ?";
    String UPDATE_USER_STATUS_BLOCKED_TRUE = "UPDATE users SET status_blocked = 1 WHERE id = ?";
    String UPDATE_USER_STATUS_BLOCKED_FALSE = "UPDATE users SET status_blocked = 0 WHERE id = ?";
    String DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login = (?)";
    String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

    //Categories
    String SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    String SELECT_CATEGORY_BY_NAME = "SELECT * FROM categories WHERE name = ?";
    String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id = ?";
    String INSERT_CATEGORY = "INSERT INTO categories (name) VALUES (?)";
    String UPDATE_CATEGORY_BY_ID = "UPDATE categories SET name = ? WHERE id = (?)";
    String UPDATE_CATEGORY_BY_NAME = "UPDATE categories SET name = ? WHERE name = (?)";
    String DELETE_CATEGORY = "DELETE FROM categories WHERE name = ?";

    //Goods
    //String INSERT_GOODS = "INSERT INTO goods (name, description, photo, price, categories_id) VALUES (?, ?, ?, ?, ?)";
    String INSERT_GOODS = "INSERT INTO goods (name, description, photo, price, categories_id, publication_date) VALUES (?, ?, ?, ?, ?, ?)";
    String SELECT_ALL_FROM_GOODS = "SELECT * FROM goods";
    String SELECT_GOODS_BY_NAME = "SELECT * FROM goods WHERE name = ?";
    String SELECT_GOODS_BY_ID = "SELECT * FROM goods WHERE id = ?";
    String SELECT_GOODS_BY_CATEGORY_ID = "SELECT * FROM goods WHERE categories_id = ?";
    String SELECT_GOODS_BY_PRICE = "SELECT * FROM goods WHERE price = ?";
    String SELECT_GOODS_BY_HIGHER_PRICE = "SELECT * FROM goods WHERE price >= ?";
    String SELECT_GOODS_BY_LOWER_PRICE = "SELECT * FROM goods WHERE price <= ?";
    String SELECT_LIMIT_GOODS = "SELECT * FROM goods LIMIT ?, ?";
    String SELECT_NUMBER_OF_GOODS = "SELECT COUNT(*) FROM goods";

    String UPDATE_GOODS = "UPDATE goods SET name = ?, description = ?, photo = ?, price = ?, categories_id = ? WHERE id = ?";
    String UPDATE_GOODS_NAME = "UPDATE goods SET name=? WHERE id = ?";
    String UPDATE_GOODS_DESCRIPTION = "UPDATE goods SET description =? WHERE id = ?";
    String UPDATE_GOODS_PROTO = "UPDATE goods SET photo=? WHERE id = ?";
    String UPDATE_GOODS_PRICE = "UPDATE goods SET price= ? WHERE id = ?";
    String UPDATE_GOODS_CATEGORY = "UPDATE goods SET categories_id = ? WHERE id = ?";
    String DELETE_GOODS_BY_ID = "DELETE FROM goods WHERE id = ?";
    String DELETE_GOODS_BY_NAME = "DELETE FROM goods WHERE name = ?";
    String SELECT_COUNT_OF_GOODS_BY_CATEGORY_NAME = "SELECT COUNT('Count') FROM goods WHERE categories_id = (SELECT id FROM categories WHERE name = ?)";

    //Orders status
    String INSERT_STATUS = "INSERT INTO orders_status (name) VALUES (?)";
    String SELECT_ALL_STATUS = "SELECT * FROM orders_status";
    String SELECT_STATUS_BY_ID = "SELECT * FROM orders_status WHERE id=?";
    String SELECT_STATUS_ID_BY_NAME = "SELECT id FROM orders_status WHERE name = ?";
    String SELECT_ORDER_STATUS_BY_ID = "SELECT * FROM orders_status WHERE id = ?";
    String UPDATE_STATUS = "UPDATE orders_status SET name='changed' WHERE id = ?";
    String DELETE_STATUS_BY_ID = "DELETE FROM orders_status WHERE id = ?";
    String DELETE_STATUS_BY_NAME = "DELETE FROM orders_status WHERE name = ?";

    //Orders
    String SELECT_ALL_FROM_ORDERS = "SELECT * FROM orders";
    String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";
    String SELECT_ORDER_BY_USER = "SELECT * FROM orders WHERE users_id = ?";
    String SELECT_ORDER_BY_GOODS = "SELECT * FROM orders WHERE goods_id = ?";
    String SELECT_ORDER_BY_STATUS = "SELECT * FROM orders WHERE orders_status_id = ?";
    String SELECT_NUMBER_OF_ORDERS = "SELECT COUNT(*) FROM orders WHERE orders_status_id = ?";
    String SELECT_NUMBER_OF_ORDERS_BY_USER = "SELECT COUNT(*) FROM orders WHERE users_id = ?";
    String SELECT_ORDER_ID_BY_NAME = "SELECT id FROM orders_status WHERE name = ?";
    String SELECT_LIMIT_ORDERS = "SELECT * FROM orders WHERE orders_status_id = ? LIMIT ?, ?";
    String SELECT_LIMIT_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE users_id = ? LIMIT ?, ?";
    String INSERT_ORDER = "INSERT INTO orders (goods_id, users_id, orders_status_id) VALUES (?, ?, ?)";
    String UPDATE_ORDER = "UPDATE orders_status SET name=? WHERE id = ?";
    String UPDATE_ORDER_STATUS = "UPDATE orders SET orders_status_id = ? WHERE id = ?";
    String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?";

    //Catalog
    String SORT_BY_NAME_GROWTH = "SELECT * FROM goods ORDER BY name ASC LIMIT ?, ?";
    String SORT_BY_NAME_DECREASE = "SELECT * FROM goods ORDER BY name DESC LIMIT ?, ?";
    String SORT_BY_PRICE_GROWTH = "SELECT * FROM goods ORDER BY price ASC LIMIT ?, ?";
    String SORT_BY_PRICE_DECREASE = "SELECT * FROM goods ORDER BY price DESC  LIMIT ?, ?";
    String SORT_BY_PUBLICATION_DATE_GROWTH = "SELECT * FROM goods ORDER BY publication_date ASC LIMIT ?, ?";
    String SORT_BY_PUBLICATION_DATE_DECREASE = "SELECT * FROM goods ORDER BY publication_date DESC LIMIT ?, ?";
    String SORT_BY_PRICE_RANGE_GROWTH = "SELECT * FROM goods WHERE price >= ? AND price <= ? ORDER BY price ASC LIMIT ?, ?";
    String SORT_BY_PRICE_RANGE_DECREASE = "SELECT * FROM goods WHERE price >= ? AND price <= ? ORDER BY price DESC LIMIT ?, ?";
    String SORT_BY_CATEGORY_GROWTH = "SELECT * FROM goods WHERE categories_id = (SELECT id FROM categories WHERE name = ?) order by name ASC LIMIT ?, ?;";
    String SORT_BY_CATEGORY_DECREASE = "SELECT * FROM goods WHERE categories_id = (SELECT id FROM categories WHERE name = ?) order by name DESC LIMIT ?, ?;";
}
