DELETE FROM usuario_expedicion;
DELETE FROM expedicion;
DELETE FROM usuario;

INSERT INTO expedicion (nombre, precio, precio_original, capacidad, categoria, imagen_url, fecha_expedicion, fecha_limite) VALUES
('Everest', 25000.00, 25000.00, 10, 3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1KP-tQScEk9gBDTSdZ3snRi0WbG7-Y2VpWQ&s', '2028-05-15', '2025-03-15'),
('K2', 22000.00, 22000.00, 8, 3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlNxfJ7U3RY7vMNE4IU4L8fa0xaL5yv2o5YA&s', '2027-07-10', '2025-05-10'),
('Kilimanjaro', 3500.00, 3500.00, 15, 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7VLSc0ttcpANX8nYf_GRVXmVHyZnO8TwPqA&s', '2027-06-20', '2025-05-20'),
('Mont Blanc', 1800.00, 2000.00, 12, 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiTWDShAJvTVWkK_esdX4Ej3A56m0qC5XRAQ&s', '2026-08-05', '2025-06-05'),
('Matterhorn', 2800.00, 3000.00, 10, 2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRk1EMFjLr9uW3ddEL5a4dlnjUkCRBqC8fpRw&s', '2026-09-12', '2025-07-12'),
('Annapurna', 20000.00, 20000.00, 6, 3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9iJf4rm___yvLagzXtEIkJsMaAoLNb509pQ&s', '2025-10-01', '2025-08-01'),
('Pirineos Traverse', 1200.00, 1200.00, 20, 0, 'https://media.iatiseguros.com/wp-content/uploads/2020/01/04012814/que-ver-dolomitas-8.jpg', '2025-07-15', '2025-05-15'),
('Alpes Austriacos', 1500.00, 1500.00, 15, 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfMp_uZzfj41J4ZfpGFiPl6qsYPI7mRcQoEg&s', '2026-08-20', '2025-07-20'),
('Aconcagua', 4500.00, 5000.00, 8, 2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQnYUyLbjWAisP7UyDW3qVEDou7eiazutwexg&s', '2027-12-10', '2025-10-10'),
('Denali', 6000.00, 6000.00, 6, 2, 'https://www.globalnationalparks.com/es/wp-content/uploads/national-park-denali.jpg', '2028-06-05', '2026-04-05');

INSERT INTO usuario (nombre, apellido, fecha_nacimiento, dni, nivel) VALUES
('Juan', 'García', '1989-05-15', '12345678A', 'INTERMEDIO'),
('María', 'López', '1996-02-20', '87654321B', 'PRINCIPIANTE'),
('Carlos', 'Martínez', '1982-11-30', '56781234C', 'AVANZADO'),
('Ana', 'Rodríguez', '1993-07-22', '43218765D', 'INTERMEDIO'),
('David', 'Sánchez', '1979-04-18', '98765432E', 'EXPERTO');

INSERT INTO usuario_expedicion (usuario_id, expedicion_id) VALUES
((SELECT id FROM usuario WHERE dni = '12345678A'), (SELECT id FROM expedicion WHERE nombre = 'Kilimanjaro')),
((SELECT id FROM usuario WHERE dni = '12345678A'), (SELECT id FROM expedicion WHERE nombre = 'Matterhorn')),
((SELECT id FROM usuario WHERE dni = '12345678A'), (SELECT id FROM expedicion WHERE nombre = 'Pirineos Traverse')),
((SELECT id FROM usuario WHERE dni = '87654321B'), (SELECT id FROM expedicion WHERE nombre = 'Mont Blanc')),
((SELECT id FROM usuario WHERE dni = '87654321B'), (SELECT id FROM expedicion WHERE nombre = 'Pirineos Traverse')),
((SELECT id FROM usuario WHERE dni = '56781234C'), (SELECT id FROM expedicion WHERE nombre = 'Everest')),
((SELECT id FROM usuario WHERE dni = '56781234C'), (SELECT id FROM expedicion WHERE nombre = 'K2')),
((SELECT id FROM usuario WHERE dni = '56781234C'), (SELECT id FROM expedicion WHERE nombre = 'Annapurna')),
((SELECT id FROM usuario WHERE dni = '43218765D'), (SELECT id FROM expedicion WHERE nombre = 'Mont Blanc')),
((SELECT id FROM usuario WHERE dni = '43218765D'), (SELECT id FROM expedicion WHERE nombre = 'Matterhorn')),
((SELECT id FROM usuario WHERE dni = '43218765D'), (SELECT id FROM expedicion WHERE nombre = 'Alpes Austriacos')),
((SELECT id FROM usuario WHERE dni = '98765432E'), (SELECT id FROM expedicion WHERE nombre = 'Everest')),
((SELECT id FROM usuario WHERE dni = '98765432E'), (SELECT id FROM expedicion WHERE nombre = 'K2')),
((SELECT id FROM usuario WHERE dni = '98765432E'), (SELECT id FROM expedicion WHERE nombre = 'Annapurna')),
((SELECT id FROM usuario WHERE dni = '98765432E'), (SELECT id FROM expedicion WHERE nombre = 'Aconcagua')),
((SELECT id FROM usuario WHERE dni = '98765432E'), (SELECT id FROM expedicion WHERE nombre = 'Denali'));