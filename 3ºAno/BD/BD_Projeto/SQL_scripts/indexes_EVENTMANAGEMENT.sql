  
CREATE INDEX idx_eventId ON EM.EVENTO(id);

CREATE INDEX idx_concertoId ON EM.CONCERTO(id);

CREATE INDEX idx_BandaId ON EM.BANDA(id);

CREATE INDEX idx_BandaGenre ON EM.BANDA(genero);

CREATE INDEX idx_Musico ON EM.MUSICO(numCC);

