-- ========================
-- 1. Insert Users
-- ========================
INSERT INTO app_user (name, email, phone) VALUES 
('Julieta Vargas', 'julieta@example.com', '5551234567'),
('Gerardo Lopez', 'gerardo@example.com', '5559876543'),
('Isaias Torres', 'isaias@example.com', '5555555555');

-- ========================
-- 2. Insert Products
-- ========================
INSERT INTO product (name, description, price) VALUES
('Lenovo Laptop', '15-inch laptop with 16GB RAM and SSD', 15000),
('Wireless Mouse', 'Compact and silent wireless mouse', 350),
('Mechanical Keyboard', 'RGB backlit mechanical keyboard', 850);

-- ========================
-- 3. Insert Carts (each assigned to a user)
-- ========================
INSERT INTO cart (user_id) VALUES
(1),
(2),
(3);

-- ========================
-- 4. Insert Cart Items
-- ========================
INSERT INTO cart_item (quantity, cart_id, product_id) VALUES
(1, 1, 1),  -- Julieta's cart: 1 Lenovo Laptop
(2, 1, 2),  -- Julieta's cart: 2 Wireless Mouse
(1, 2, 3);  -- Gerardo's cart: 1 Mechanical Keyboard
