INSERT INTO TB_ITEM (_name, price) VALUES 
('Pen',5),
('Book',10),
('Bag',30),
('Pencil',3),
('Shoe',45),
('Box',5),
('Cap',25);


INSERT INTO TB_INVENTORY (item_id,qty,_type) VALUES 
(1,5,'T'),
(2,10,'T'),
(3,30,'T'),
(4,3,'T'),
(5,45,'T'),
(6,5,'T'),
(7,25,'T'),
(5,10,'W');

INSERT INTO TB_ORDER (item_id, qty) VALUES
(1,2),
(2,3),
(3,4),
(4,1),
(5,2),
(6,3);