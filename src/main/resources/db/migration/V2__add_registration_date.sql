--
-- Fiel: V2__add_registration_date.sql
-- Description: add the registration date column to the customer table
--
ALTER TABLE clients ADD COLUMN registration_date DATE NOT NULL;