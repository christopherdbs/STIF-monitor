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
('Montparnasse');

-- 2. Insert into EQUIPMENTS
INSERT INTO equipments (serial_number, station_id, type, status) VALUES
('ESC-402', 1, 'ESCALATOR', 'Operational'),
('ELEV-105', 2, 'ELEVATOR', 'Under Maintenance'),
('FLAT-99', 3, 'FLAT_ESCALATOR', 'Broken');

-- 3. Insert into INCIDENT_REPORTS
INSERT INTO incident_reports (nature, date_time, comments, is_repaired, equipment_id, agent_id, assigned_company)
VALUES
('Broken step', DATE_SUB(NOW(), INTERVAL 5 HOUR), 'Reported by a passenger this morning.', false, 1, 1, NULL),
('Abnormal noise', DATE_SUB(NOW(), INTERVAL 50 HOUR), 'Waiting for technical diagnosis.', false, 2, 1, NULL),
('Total shutdown', DATE_SUB(NOW(), INTERVAL 80 HOUR), 'Major engine failure detected.', false, 3, 1, NULL),
('Emergency button stuck', DATE_SUB(NOW(), INTERVAL 200 HOUR), 'Fixed by the company OTIS.', true, 1, 1, 'OTIS');