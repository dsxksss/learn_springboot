CREATE TABLE IF NOT EXISTS `coffees`(
    `id` VARCHAR(50) PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL,
    `price` DOUBLE NOT NULL,
    `quantity` INTEGER NOT NULL,
    `image` VARCHAR(100) NOT NULL,
    `description` TEXT NOT NULL,
    `createTime` BIGINT NOT NULL
);

