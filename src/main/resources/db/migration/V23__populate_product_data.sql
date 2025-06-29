-- Insert categories
INSERT INTO categories (name) VALUES
('Guitars'),
('Keyboards & Pianos'),
('Drums & Percussion'),
('Wind Instruments');

-- Insert products
INSERT INTO products (name, price, category_id, description) VALUES
-- Guitars (category_id = 1)
('Fender Player Stratocaster', 799.99, 1, 'Classic electric guitar with alder body, maple neck, and three single-coil pickups. Perfect for rock, blues, and pop music.'),
('Taylor 814ce Acoustic Guitar', 3499.00, 1, 'Premium acoustic-electric guitar with Indian rosewood back and sides, Sitka spruce top, and Expression System 2 electronics.'),
('Gibson Les Paul Standard', 2499.00, 1, 'Iconic electric guitar featuring mahogany body with maple cap, dual humbucking pickups, and traditional weight relief.'),
('Yamaha FG830 Acoustic Guitar', 199.99, 1, 'Beginner-friendly acoustic guitar with solid sitka spruce top, rosewood fingerboard, and die-cast tuners.'),

-- Keyboards & Pianos (category_id = 2)
('Yamaha P-125 Digital Piano', 649.99, 2, '88-key weighted digital piano with Graded Hammer Standard action and high-quality piano samples from Yamaha CFX concert grand.'),
('Roland FP-30X Digital Piano', 799.00, 2, 'Portable digital piano with SuperNATURAL Piano sound engine, PHA-4 Standard keyboard, and built-in speakers.'),
('Korg SV-2 Stage Piano', 1899.99, 2, '88-key stage piano with vintage electric pianos, organs, and acoustic pianos. Features real wood keys and tube preamp modeling.'),
('Casio CDP-S110 Digital Piano', 449.99, 2, 'Compact 88-key digital piano with scaled hammer action, 10 built-in tones, and USB connectivity.'),

-- Drums & Percussion (category_id = 3)
('Pearl Export 5-Piece Drum Set', 699.99, 3, 'Complete drum kit with poplar/Asian mahogany shells, including bass drum, snare, two toms, floor tom, and hardware.'),
('DW Performance Series 4-Piece Kit', 1599.00, 3, 'Professional drum set with maple shells, STM suspension tom mounts, and True-Pitch rod system.'),
('Zildjian A Custom Cymbal Pack', 899.99, 3, 'Cymbal set including 14" hi-hats, 16" crash, 18" crash, and 20" medium ride. Known for bright, cutting sound.'),
('Roland TD-17KVX V-Drums', 1699.99, 3, 'Electronic drum set with mesh heads, KD-10 kick pad, and TD-17 sound module with 310 sounds and coaching functions.'),

-- Wind Instruments (category_id = 4)
('Yamaha YAS-280 Alto Saxophone', 1199.99, 4, 'Student alto saxophone with yellow brass body, nickel-plated keys, and includes mouthpiece, ligature, and case.'),
('Bach TR300H2 Trumpet', 499.99, 4, 'Student trumpet with yellow brass bell, stainless steel pistons, and includes 7C mouthpiece and lightweight case.'),
('Buffet Crampon E11 Clarinet', 899.00, 4, 'Intermediate clarinet with grenadilla wood body, silver-plated keys, and adjustable thumb rest. Includes case and accessories.');