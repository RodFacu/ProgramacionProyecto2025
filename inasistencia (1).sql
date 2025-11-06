-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-11-2025 a las 15:48:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inasistencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `usuario` varchar(50) NOT NULL,
  `contrasena` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`usuario`, `contrasena`) VALUES
('juanjo', '21'),
('leo', '12'),
('marcela', '22');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE `grupo` (
  `id_grupo` int(11) DEFAULT NULL,
  `nombre_grupo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `grupo`
--

INSERT INTO `grupo` (`id_grupo`, `nombre_grupo`) VALUES
(6, 'guftyuygiuhoiu'),
(8, 'iuhhui'),
(2, 'ma');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inasistencia`
--

CREATE TABLE `inasistencia` (
  `cedula` int(11) NOT NULL,
  `id_licencia` int(11) NOT NULL,
  `fecha_inicio` varchar(20) DEFAULT NULL,
  `fecha_cierre` varchar(20) DEFAULT NULL,
  `justificacion` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inasistencia`
--

INSERT INTO `inasistencia` (`cedula`, `id_licencia`, `fecha_inicio`, `fecha_cierre`, `justificacion`) VALUES
(7980, 26, '66556', '778904', 'hyhiuuhjioklo'),
(78787, 25, '67890', '7886787', 'tgyuhiujmkl'),
(48773615, 27, '6.10.2025', '12.12.2025', 'Art por estudio');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `licencia`
--

CREATE TABLE `licencia` (
  `id_licencia` int(11) NOT NULL,
  `fecha_inicio` varchar(20) DEFAULT NULL,
  `fecha_cierre` varchar(20) DEFAULT NULL,
  `justificacion` varchar(100) DEFAULT NULL,
  `cedula` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `licencia`
--

INSERT INTO `licencia` (`id_licencia`, `fecha_inicio`, `fecha_cierre`, `justificacion`, `cedula`) VALUES
(25, '67890', '7886787', 'tgyuhiujmkl', 78787),
(26, '66556', '778904', 'hyhiuuhjioklo', 7980),
(27, '6.10.2025', '12.12.2025', 'Art por estudio', 48773615);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE `profesor` (
  `cedula` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `telefono` int(11) DEFAULT NULL,
  `turno` varchar(20) DEFAULT NULL,
  `id_grupo` int(11) DEFAULT NULL,
  `materias` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `profesor`
--

INSERT INTO `profesor` (`cedula`, `nombre`, `apellido`, `telefono`, `turno`, `id_grupo`, `materias`) VALUES
(7980, 'ftdrfyguhuijohy', 'tfyguhipkujhiyug', 1235468, 'hgfrdedryguh', 8, 'ojhiuyghi'),
(78787, 'fytugyhinjmjhibyug', 'rhlkmojhiyugt', 9779889, 'jouhiygu', 6, 'gtyiuhiyguiuh'),
(48773615, 'marcela', 'mederos', 23456789, 'matutino', 2, 'programacion');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registra`
--

CREATE TABLE `registra` (
  `id_admin` varchar(50) NOT NULL,
  `cedula` int(11) NOT NULL,
  `id_licencia` int(11) NOT NULL,
  `fecha_registro` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `registra`
--

INSERT INTO `registra` (`id_admin`, `cedula`, `id_licencia`, `fecha_registro`) VALUES
('leo', 7980, 26, '66556'),
('leo', 78787, 25, '67890'),
('marcela', 48773615, 27, '6.10.2025');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`usuario`);

--
-- Indices de la tabla `inasistencia`
--
ALTER TABLE `inasistencia`
  ADD PRIMARY KEY (`cedula`,`id_licencia`),
  ADD KEY `id_licencia` (`id_licencia`);

--
-- Indices de la tabla `licencia`
--
ALTER TABLE `licencia`
  ADD PRIMARY KEY (`id_licencia`),
  ADD KEY `cedula` (`cedula`);

--
-- Indices de la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD PRIMARY KEY (`cedula`);

--
-- Indices de la tabla `registra`
--
ALTER TABLE `registra`
  ADD PRIMARY KEY (`id_admin`,`cedula`,`id_licencia`),
  ADD KEY `cedula` (`cedula`),
  ADD KEY `id_licencia` (`id_licencia`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `licencia`
--
ALTER TABLE `licencia`
  MODIFY `id_licencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `inasistencia`
--
ALTER TABLE `inasistencia`
  ADD CONSTRAINT `inasistencia_ibfk_1` FOREIGN KEY (`cedula`) REFERENCES `profesor` (`cedula`),
  ADD CONSTRAINT `inasistencia_ibfk_2` FOREIGN KEY (`id_licencia`) REFERENCES `licencia` (`id_licencia`);

--
-- Filtros para la tabla `licencia`
--
ALTER TABLE `licencia`
  ADD CONSTRAINT `licencia_ibfk_1` FOREIGN KEY (`cedula`) REFERENCES `profesor` (`cedula`);

--
-- Filtros para la tabla `registra`
--
ALTER TABLE `registra`
  ADD CONSTRAINT `registra_ibfk_1` FOREIGN KEY (`cedula`) REFERENCES `profesor` (`cedula`),
  ADD CONSTRAINT `registra_ibfk_2` FOREIGN KEY (`id_licencia`) REFERENCES `licencia` (`id_licencia`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
