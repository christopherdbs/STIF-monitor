CREATE DATABASE IF NOT EXISTS STIF_db
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci ;
USE STIF_db;

CREATE TABLE stations(
id INT AUTO_INCREMENT PRIMARY KEY,
station_name VARCHAR(100) NOT NULL
);

CREATE TABLE equipments(
id INT AUTO_INCREMENT PRIMARY KEY,
serial_number VARCHAR(20) NOT NULL UNIQUE,
status VARCHAR(50) NOT NULL,
station_id INT NOT NULL,
type VARCHAR(20) NOT NULL
);

CREATE TABLE incident_reports (
id INT AUTO_INCREMENT PRIMARY KEY,
nature VARCHAR(255) NOT NULL,
comments TEXT,
date_time DATETIME,
assigned_company VARCHAR(255),
is_repaired BOOLEAN DEFAULT FALSE,
equipment_id INT NOT NULL,
agent_id INT NOT NULL,
CONSTRAINT fk_incident_equipment
FOREIGN KEY (equipment_id)
REFERENCES equipments(id)
ON DELETE CASCADE
);


-- 1. Insert into STATIONS first (so the IDs exist for the foreign keys)
INSERT INTO stations (station_name) VALUES
('Gare du Nord'),
('Chatelet-Les Halles'),
('Montparnasse'),
('Lyon Part-Dieu'),
('Lille Flandres');

-- 2. Insert into EQUIPMENTS
INSERT INTO equipments (serial_number, station_id, type, status) VALUES
('ESC-402', 1, 'ESCALATOR', 'Operational'),
('ELEV-105', 2, 'ELEVATOR', 'Under Maintenance'),
('FLAT-99', 3, 'FLAT_ESCALATOR', 'Broken'),
('ESC-505', 4, 'ESCALATOR', 'Broken'),
('ELEV-202', 5, 'ELEVATOR', 'Under Maintenance');

-- 3. Insert into INCIDENT_REPORTS
INSERT INTO incident_reports (nature, date_time, comments, is_repaired, equipment_id, agent_id, assigned_company)
VALUES
('Broken step', DATE_SUB(NOW(), INTERVAL 5 HOUR), 'Reported by a passenger this morning.', false, 1, 1, NULL),
('Abnormal noise', DATE_SUB(NOW(), INTERVAL 50 HOUR), 'Waiting for technical diagnosis.', false, 2, 1, NULL),
('Total shutdown', DATE_SUB(NOW(), INTERVAL 80 HOUR), 'Major engine failure detected.', false, 3, 1, NULL),
('Emergency button stuck', DATE_SUB(NOW(), INTERVAL 200 HOUR), 'Fixed by the company OTIS.', true, 1, 1, 'OTIS');

INSERT INTO incident_reports (nature, date_time, comments, is_repaired, equipment_id, agent_id, assigned_company) VALUES
-- Équipment 1 (Gare du Nord)
('Chain lubrication', DATE_SUB(NOW(), INTERVAL 15 DAY), 'Standard bi-weekly maintenance.', true, 1, 1, 'KONE'),
('Key switch replacement', DATE_SUB(NOW(), INTERVAL 10 DAY), 'Worn out start switch replaced.', true, 1, 1, 'KONE'),
('Step alignment check', DATE_SUB(NOW(), INTERVAL 5 DAY), 'Verified all steps are centered.', true, 1, 1, 'INTERNAL'),
('Optical sensor failure', DATE_SUB(NOW(), INTERVAL 1 HOUR), 'Automatic start not working.', false, 1, 1, NULL),

-- Équipment 2 (Chatelet)
('Leveling adjustment', DATE_SUB(NOW(), INTERVAL 22 DAY), 'Floor alignment corrected (+2mm).', true, 2, 1, 'OTIS'),
('Cabin light out', DATE_SUB(NOW(), INTERVAL 18 DAY), 'LED panel replaced.', true, 2, 1, 'INTERNAL'),
('Call button 3rd floor', DATE_SUB(NOW(), INTERVAL 12 DAY), 'Contact cleaned, now responding.', true, 2, 1, 'OTIS'),
('Door obstruction', DATE_SUB(NOW(), INTERVAL 30 MINUTE), 'Objects stuck in the lower track.', false, 2, 1, NULL),

-- Équipment 3 (Montparnasse)
('Strange vibration', DATE_SUB(NOW(), INTERVAL 40 DAY), 'Tightened drive belt.', true, 3, 1, 'SCHINDLER'),
('Emergency stop test', DATE_SUB(NOW(), INTERVAL 30 DAY), 'Quarterly safety certification.', true, 3, 1, 'APAVE'),
('Control panel reboot', DATE_SUB(NOW(), INTERVAL 20 DAY), 'Software glitch resolved.', true, 3, 1, 'INTERNAL'),
('Motor overheating', DATE_SUB(NOW(), INTERVAL 4 HOUR), 'Thermal protection tripped.', false, 3, 1, 'SCHINDLER'),

-- Équipment 4 (Lyon Part-Dieu)
('Squeaky steps', DATE_SUB(NOW(), INTERVAL 30 DAY), 'Lubrication applied.', true, 4, 1, 'SCHINDLER'),
('Handrail stuck', DATE_SUB(NOW(), INTERVAL 20 DAY), 'Cleaned the mechanism.', true, 4, 1, 'INTERNAL'),
('Unusual vibration', DATE_SUB(NOW(), INTERVAL 15 DAY), 'Minor adjustment on the motor.', true, 4, 1, 'SCHINDLER'),
('Scheduled cleaning', DATE_SUB(NOW(), INTERVAL 5 DAY), 'Monthly maintenance.', true, 4, 1, 'CLEAN-CO'),
('Chain breakage', DATE_SUB(NOW(), INTERVAL 2 HOUR), 'Critical failure, stairs blocked.', false, 4, 1, NULL),

-- Équipment 5 (Lille Flandres)
('Light flickering', DATE_SUB(NOW(), INTERVAL 45 DAY), 'Bulbs replaced.', true, 5, 1, 'INTERNAL'),
('Doors closing slowly', DATE_SUB(NOW(), INTERVAL 25 DAY), 'Sensor recalibrated.', true, 5, 1, 'OTIS'),
('Strange smell', DATE_SUB(NOW(), INTERVAL 12 DAY), 'Checked electrical panel, no issue found.', true, 5, 1, 'OTIS'),
('Emergency phone test', DATE_SUB(NOW(), INTERVAL 4 DAY), 'Routine check, communication OK.', true, 5, 1, 'INTERNAL'),
('Electronic board failure', DATE_SUB(NOW(), INTERVAL 6 HOUR), 'System unresponsive, waiting for parts.', false, 5, 1, 'OTIS');