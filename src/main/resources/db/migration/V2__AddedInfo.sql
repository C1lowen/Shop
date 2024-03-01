INSERT INTO brand (brand)
VALUES
    ('Adidas'),
    ('New Balance'),
    ('Skechers'),
    ('Nike');


INSERT INTO color (color)
VALUES
    ('Black'),
    ('White-Black'),
    ('White'),
    ('Red'),
    ('Gray');


INSERT INTO gender (gender)
VALUES
    ('Men'),
    ('Women');


INSERT INTO roles (role)
VALUES
    ('User'),
    ('Admin');

INSERT INTO promo (promo_name, discount, count_activation, active_date)
VALUES  ('zfeS1K', 30, 300, '2024-12-12'),
        ('anotherPromo', 20, 200, '2024-11-11'),
        ('AROMASHOP', 10, 150, '2024-10-10');


INSERT INTO specification (size, purpose, material, membrane, country, description, short_description)
VALUES
    ('39-45', 'For everyday wear', 'Polyurethane', false, 'Vietnam',
     'Stylish and comfortable New Balance 530 MR530SMT sneakers are suitable for men who prefer an active lifestyle. Their design reflects the best traditions of the brand',
     'Stylish and comfortable New Balance sneakers for men'),
     ('39-45', 'For everyday wear', 'Polyurethane', true, 'Vietnam',
     'New Balance 608 MX608WT is a classic mens sneaker for everyday wear. Comfort and stability make them a great choice for an active lifestyle',
     'Classic New Balance sneakers for everyday activities'),
    ('39-45', 'For everyday wear', 'Polyurethane', false, 'Turkey',
     'Adidas Response Runner-U ID7336 - running sneakers with a modern design. Cushioning and support technologies make them a great choice for active workouts',
     'Modern Adidas running shoes for everyone to wear.'),
    ('39-45', 'For everyday wear', 'Synthetics', true, 'Turkey',
     'Inspired by 80s style, the classic New Balance 574 ML574EVN mens sneaker offers a combination of exceptional comfort and vintage design. Ideal for everyday active life',
     'Vintage New Balance mens sneakers with everyday comfort'),
    ('39-45', 'For everyday wear', 'Polyurethane', false, 'USA',
     'Lightweight and functional, the Adidas Ozelle GX6767 sneakers are suitable for outdoor activities. Thoughtful design and technology provide comfort and support in any situation',
     'Lightweight and versatile adidas running shoes for outdoor activities'),
    ('39-45', 'For everyday wear', 'Synthetics', true, 'USA',
     'Sleek and versatile, the New Balance 574 ML574EVE is designed for style and comfort. Durable materials and modern design highlight your individuality',
     'Sleek New Balance sneakers with comfortable support'),
    ('39-45', 'For everyday wear', 'Synthetics', true, 'USA',
     'The adidas Ax2S Q46587 is a running shoe designed for an active lifestyle. Durable upper and cushioning technology provide comfort and support during your workouts',
     'Adidas running shoes for an active lifestyle'),
    ('38-45', 'For everyday wear', 'Synthetics', true, 'USA',
     'The Nike Precision Vi DD9535-007 is a basketball shoe that features innovative Zoom Air technology for soft cushioning. Ideal choice for basketball fans',
     'Innovative Nike basketball shoes for confident movement on the court'),
    ('38-45', 'For everyday wear', 'Synthetics', true, 'USA',
     'Skechers 144521 BKW sneakers combine style and comfort. Lightweight and breathable material with a modern design makes them a great choice for everyday wear',
     'Stylish and lightweight sneakers from Skechers for an active lifestyle'),
    ('38-45', 'For everyday wear', 'Synthetics', true, 'USA',
     'The Gore Tex Adidas Terrex Swift R3 Gtx is designed for trail running and active outdoor activities. Waterproof Gore-Tex technology provides protection in all conditions',
     'Waterproof Adidas Trail & Adventure Shoes'),
    ('38-45', 'For everyday wear', 'Synthetics', true, 'USA',
     'Skechers 896044 BLK - stylish and comfortable sneakers. Air-Cooled Memory Foam technology provides soft cushioning, making them ideal for everyday wear',
     'Stylish and comfortable Skechers sneakers for everyday wear');


INSERT INTO products (id, images, name, price, color, category, gender, specification)
VALUES
    (1, '/img/product/New-Balance-530-MR530SMT-42.5.png', 'New Balance 530 MR530SMT', 200, 1, 2, 1, 1),
    (2, '/img/product/New-Balance-608-MX608WT-47.png', 'New Balance 608 MX608WT', 100, 4, 2, 2, 2),
    (3, '/img/product/Adidas-Response-Runner-U-ID7336-42.5.png', 'Adidas Response Runner-U ID7336', 150, 3, 1, 1, 3),
    (4, '/img/product/New-Balance-574-ML574EVN-44.png', 'New Balance 574 ML574EVN', 300, 4, 2, 1, 4),
    (5, '/img/product/Adidas-Ozelle-GX6767-36.png', 'Adidas Ozelle GX6767', 210, 2, 1, 2, 5),
    (6, '/img/product/New-Balance-574-ML574EVE-41.5.png', 'New Balance 574 ML574EVE', 430, 1, 2, 1, 6),
    (7, '/img/product/Adidas-Ax2S-Q46587-41.5.png', 'Adidas Ax2S Q46587', 90, 1, 1, 2, 7),
    (8, '/img/product/Nike-Precision-Vi-DD9535-007-45.png', 'Nike Precision Vi DD9535-007', 140, 2, 4, 1, 8),
    (9, '/img/product/Skechers-144521-BKW-37.png', 'Skechers 144521 BKW', 230, 5, 3, 1, 9),
    (10, '/img/product/Gore-Tex-Adidas-Terrex-Swift-R3-Gtx.png', 'Gore Tex Adidas Terrex Swift R3 Gtx', 320, 3, 1, 2, 10),
    (11, '/img/product/Skechers-896044-BLK-41.png', 'Skechers 896044 BLK', 230, 5, 3, 1, 11)


