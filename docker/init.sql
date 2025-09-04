--
-- File: init.sql
-- Description: Script for database initialization.
--

-- Create the 'clients' table
CREATE TABLE clients (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Create the 'orders' table
CREATE TABLE orders (
    id VARCHAR(255) PRIMARY KEY,
    order_date DATE NOT NULL,
    total DOUBLE NOT NULL,
    details TEXT,
    client_id VARCHAR(255),
    FOREIGN KEY (client_id) REFERENCES clients(id)
);