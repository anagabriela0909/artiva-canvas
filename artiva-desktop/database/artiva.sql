-- =====================================================================
-- Artiva - Banco de dados MySQL
-- Importar no MySQL Workbench: File > Open SQL Script > Execute
-- =====================================================================

DROP DATABASE IF EXISTS artiva;
CREATE DATABASE artiva
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE artiva;

-- ---------------------------------------------------------------------
-- TABELAS
-- ---------------------------------------------------------------------

CREATE TABLE usuarios (
    id_usuario    INT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,
    senha         VARCHAR(255) NOT NULL,
    tipo_usuario  ENUM('USUARIO','ADMINISTRADOR') NOT NULL DEFAULT 'USUARIO',
    data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE autores (
    id_autor      INT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(150) NOT NULL,
    biografia     TEXT,
    periodo       VARCHAR(100),
    nacionalidade VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE estilos (
    id_estilo   INT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL UNIQUE,
    descricao   TEXT NOT NULL,
    dificuldade ENUM('INICIANTE','INTERMEDIARIO','AVANCADO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tecnicas (
    id_tecnica  INT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL UNIQUE,
    descricao   TEXT NOT NULL,
    dificuldade ENUM('INICIANTE','INTERMEDIARIO','AVANCADO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE materiais (
    id_material INT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL UNIQUE,
    descricao   TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE obras (
    id_obra            INT AUTO_INCREMENT PRIMARY KEY,
    titulo             VARCHAR(200) NOT NULL,
    descricao          TEXT NOT NULL,
    ano                VARCHAR(50),
    imagem             VARCHAR(255),
    dificuldade        ENUM('INICIANTE','INTERMEDIARIO','AVANCADO') NOT NULL,
    contexto_historico TEXT,
    curiosidades       TEXT,
    data_cadastro      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_autor           INT NOT NULL,
    id_estilo          INT NOT NULL,
    id_tecnica         INT NOT NULL,
    CONSTRAINT fk_obras_autor FOREIGN KEY (id_autor)
        REFERENCES autores(id_autor) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_obras_estilo FOREIGN KEY (id_estilo)
        REFERENCES estilos(id_estilo) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_obras_tecnica FOREIGN KEY (id_tecnica)
        REFERENCES tecnicas(id_tecnica) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE obra_materiais (
    id_obra     INT NOT NULL,
    id_material INT NOT NULL,
    PRIMARY KEY (id_obra, id_material),
    CONSTRAINT fk_om_obra FOREIGN KEY (id_obra)
        REFERENCES obras(id_obra) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_om_material FOREIGN KEY (id_material)
        REFERENCES materiais(id_material) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE favoritos (
    id_usuario      INT NOT NULL,
    id_obra         INT NOT NULL,
    data_favoritado DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_obra),
    CONSTRAINT fk_fav_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_fav_obra FOREIGN KEY (id_obra)
        REFERENCES obras(id_obra) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE conteudos_educativos (
    id_conteudo   INT AUTO_INCREMENT PRIMARY KEY,
    titulo        VARCHAR(200) NOT NULL,
    descricao     TEXT NOT NULL,
    categoria     ENUM('TECNICA','MATERIAL','HISTORIA','FUNDAMENTOS','DICAS') NOT NULL,
    dificuldade   ENUM('INICIANTE','INTERMEDIARIO','AVANCADO') NOT NULL,
    tempo_leitura INT,
    imagem        VARCHAR(255),
    data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- DADOS INICIAIS
-- ---------------------------------------------------------------------

INSERT INTO usuarios (nome, email, senha, tipo_usuario) VALUES
('Administrador', 'admin@artiva.com', 'admin123', 'ADMINISTRADOR');

INSERT INTO estilos (nome, descricao, dificuldade) VALUES
('Impressionismo', 'Movimento surgido na França no século XIX que abandona o detalhe minucioso em favor da luz e da atmosfera. As pinceladas são soltas e visíveis, e as cenas costumam ser pintadas ao ar livre.', 'INTERMEDIARIO'),
('Pós-Impressionismo', 'Reação ao Impressionismo em que a cor deixa de descrever a luz e passa a expressar emoção. A forma é deformada de modo intencional e a pincelada carrega a subjetividade do artista.', 'AVANCADO'),
('Realismo', 'Representação fiel da vida cotidiana, sem idealização. Valoriza o trabalho, a paisagem comum e a observação atenta do mundo tal como ele é.', 'INTERMEDIARIO'),
('Barroco', 'Estilo do século XVII marcado pelo contraste dramático entre luz e sombra, movimento intenso e forte teatralidade nas composições.', 'AVANCADO'),
('Expressionismo', 'Distorce cor e forma para revelar estados interiores como angústia, medo e solidão. A fidelidade visual é sacrificada em nome da intensidade emocional.', 'INTERMEDIARIO'),
('Surrealismo', 'Explora o sonho e o inconsciente, combinando objetos reais em situações impossíveis para provocar estranhamento no observador.', 'AVANCADO'),
('Aquarela', 'Linguagem baseada na transparência: camadas finas de pigmento diluído em água deixam o branco do papel atuar como luz da composição.', 'INICIANTE');

INSERT INTO tecnicas (nome, descricao, dificuldade) VALUES
('Impasto', 'Aplicação de tinta em camadas espessas com pincel ou espátula, deixando relevo visível na superfície. A luz incide sobre a massa de tinta e cria movimento.', 'AVANCADO'),
('Aquarela', 'Pintura com pigmento diluído em água em camadas transparentes, do tom mais claro para o mais escuro, preservando o branco do papel.', 'INICIANTE'),
('Pincelada', 'Uso da marca do pincel como elemento expressivo: direção, pressão e carga de tinta definem textura e ritmo da imagem.', 'INICIANTE'),
('Esfumado', 'Transição suave entre tons, sem contornos duros, obtida esfregando ou mesclando as cores ainda frescas.', 'INTERMEDIARIO'),
('Pontilhismo', 'Construção da imagem por pequenos pontos de cor pura que se misturam no olho do observador à distância.', 'INTERMEDIARIO'),
('Veladura', 'Camadas finas e translúcidas de tinta sobre uma base seca, alterando a cor por sobreposição e dando profundidade luminosa.', 'AVANCADO');

INSERT INTO materiais (nome, descricao) VALUES
('Tinta a óleo', 'Pigmento em base oleosa, de secagem lenta, que permite misturas longas e cores profundas.'),
('Tinta acrílica', 'Pigmento em base aquosa de secagem rápida, versátil sobre tela, papel e madeira.'),
('Aquarela', 'Pigmento em pastilha ou tubo diluído em água, usado em camadas transparentes.'),
('Tela', 'Suporte de algodão ou linho esticado em chassi, base tradicional da pintura.'),
('Pincel', 'Ferramenta de aplicação de tinta; formato e cerda definem o tipo de marca.'),
('Espátula', 'Lâmina metálica usada para aplicar massa de tinta e criar relevo.'),
('Paleta', 'Superfície onde as cores são misturadas antes da aplicação.');

INSERT INTO autores (nome, biografia, periodo, nacionalidade) VALUES
('Vincent van Gogh', 'Pintou mais de 2.000 obras em pouco mais de dez anos. Sua pincelada espessa e vibrante transformou a paisagem em emoção pura e abriu caminho para a arte moderna.', '1853 - 1890', 'Holandesa'),
('Claude Monet', 'Fundador do Impressionismo, pintava a mesma cena em horários diferentes para estudar as variações da luz. Construiu o jardim de Giverny para servir de modelo.', '1840 - 1926', 'Francesa'),
('Katsushika Hokusai', 'Mestre da gravura ukiyo-e, autor da série Trinta e Seis Vistas do Monte Fuji. Trocou de nome artístico mais de trinta vezes ao longo da carreira.', '1760 - 1849', 'Japonesa'),
('Edvard Munch', 'Precursor do Expressionismo, investigou angústia, amor e morte. Produziu quatro versões de O Grito em técnicas diferentes.', '1863 - 1944', 'Norueguesa');

INSERT INTO obras (titulo, descricao, ano, imagem, dificuldade, contexto_historico, curiosidades, id_autor, id_estilo, id_tecnica) VALUES
('A Noite Estrelada',
 'Um céu em espiral sobre um vilarejo adormecido, com o cipreste ligando terra e céu em uma única pincelada contínua.',
 '1889', 'noite_estrelada.jpg', 'AVANCADO',
 'Pintada durante a internação no sanatório de Saint-Rémy-de-Provence, a partir da vista da janela combinada com memória e imaginação.',
 'Van Gogh considerava a obra um fracasso: dizia ter exagerado nas estrelas.',
 (SELECT id_autor FROM autores WHERE nome='Vincent van Gogh'),
 (SELECT id_estilo FROM estilos WHERE nome='Pós-Impressionismo'),
 (SELECT id_tecnica FROM tecnicas WHERE nome='Impasto')),
('Impressão, Nascer do Sol',
 'O porto de Le Havre envolvido em neblina, com o sol laranja refletido na água em pinceladas rápidas.',
 '1872', 'impressao_nascer_do_sol.jpg', 'INTERMEDIARIO',
 'O título da obra deu nome ao Impressionismo, usado inicialmente como crítica pela imprensa da época.',
 'Monet pintou a cena em poucas horas para capturar a luz exata do amanhecer.',
 (SELECT id_autor FROM autores WHERE nome='Claude Monet'),
 (SELECT id_estilo FROM estilos WHERE nome='Impressionismo'),
 (SELECT id_tecnica FROM tecnicas WHERE nome='Pincelada')),
('Nenúfares',
 'Superfície de água coberta por vitórias-régias, sem horizonte nem margem visível, apenas reflexo e cor.',
 '1899–1926', 'nenufares.jpg', 'INTERMEDIARIO',
 'Parte da série pintada no jardim de Giverny nos últimos anos de vida do artista.',
 'Monet pintou cerca de 250 telas com o mesmo lago.',
 (SELECT id_autor FROM autores WHERE nome='Claude Monet'),
 (SELECT id_estilo FROM estilos WHERE nome='Impressionismo'),
 (SELECT id_tecnica FROM tecnicas WHERE nome='Pincelada')),
('A Grande Onda de Kanagawa',
 'Uma onda gigante com garras de espuma prestes a cobrir três barcos, com o Monte Fuji pequeno ao fundo.',
 'c. 1831', 'grande_onda.jpg', 'AVANCADO',
 'Gravura da série Trinta e Seis Vistas do Monte Fuji, produzida em larga escala e vendida a preços populares no Japão do período Edo.',
 'O azul intenso vem do pigmento Azul da Prússia, recém-importado da Europa.',
 (SELECT id_autor FROM autores WHERE nome='Katsushika Hokusai'),
 (SELECT id_estilo FROM estilos WHERE nome='Realismo'),
 (SELECT id_tecnica FROM tecnicas WHERE nome='Aquarela')),
('O Grito',
 'Figura de rosto deformado em uma ponte, sob um céu ondulado em laranja e ocre.',
 '1893', 'o_grito.jpg', 'AVANCADO',
 'Munch relatou em seu diário ter sentido um grito atravessar a natureza durante um passeio ao anoitecer em Oslo.',
 'Existe uma inscrição a lápis na tela, provavelmente escrita pelo próprio artista.',
 (SELECT id_autor FROM autores WHERE nome='Edvard Munch'),
 (SELECT id_estilo FROM estilos WHERE nome='Expressionismo'),
 (SELECT id_tecnica FROM tecnicas WHERE nome='Esfumado'));

INSERT INTO obra_materiais (id_obra, id_material)
SELECT o.id_obra, m.id_material FROM obras o JOIN materiais m
WHERE (o.titulo = 'A Noite Estrelada'          AND m.nome IN ('Tinta a óleo','Tela','Espátula','Pincel'))
   OR (o.titulo = 'Impressão, Nascer do Sol'   AND m.nome IN ('Tinta a óleo','Tela','Pincel','Paleta'))
   OR (o.titulo = 'Nenúfares'                  AND m.nome IN ('Tinta a óleo','Tela','Pincel'))
   OR (o.titulo = 'A Grande Onda de Kanagawa'  AND m.nome IN ('Aquarela','Pincel'))
   OR (o.titulo = 'O Grito'                    AND m.nome IN ('Tinta acrílica','Pincel','Paleta'));

INSERT INTO conteudos_educativos (titulo, descricao, categoria, dificuldade, tempo_leitura, imagem) VALUES
('Como funciona a pintura a óleo?', 'Camadas, tempo de secagem e a regra do gordo sobre magro explicados sem jargão.', 'MATERIAL', 'INICIANTE', 6, NULL),
('O que é perspectiva?', 'Como criar profundidade usando linhas de fuga e um único ponto de observação.', 'FUNDAMENTOS', 'INTERMEDIARIO', 5, NULL),
('Como escolher um pincel?', 'Formatos, tipos de cerda e para que serve cada um na prática.', 'MATERIAL', 'INICIANTE', 4, NULL),
('O que são cores complementares?', 'Pares opostos no círculo cromático e por que criam contraste vibrante.', 'FUNDAMENTOS', 'INICIANTE', 3, NULL),
('Introdução à aquarela', 'Água, transparência e o valor do branco do papel nas primeiras camadas.', 'TECNICA', 'INICIANTE', 5, NULL);

-- Favorito de exemplo (o administrador salva A Noite Estrelada)
INSERT INTO favoritos (id_usuario, id_obra)
SELECT u.id_usuario, o.id_obra FROM usuarios u, obras o
WHERE u.email = 'admin@artiva.com' AND o.titulo = 'A Noite Estrelada';
