CREATE TABLE IF NOT EXISTS comments (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rate int,
    body text,
    created_at timestamp default now(),
    product_id int,
    INDEX product_ind (product_id),
    FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE,
    user_id int,
    INDEX user_ind (user_id),
    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=UTF8;