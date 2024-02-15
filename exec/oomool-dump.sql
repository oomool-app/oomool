-- --------------------------------------------------------
-- 호스트:                          i10a809.p.ssafy.io
-- 서버 버전:                        11.2.2-MariaDB-1:11.2.2+maria~ubu2204 - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- oomool 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `oomool` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `oomool`;

-- 테이블 oomool.avatar 구조 내보내기
CREATE TABLE IF NOT EXISTS `avatar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 oomool.avatar:~9 rows (대략적) 내보내기
DELETE FROM `avatar`;
INSERT INTO `avatar` (`id`, `url`) VALUES
	(1, '/img/bearGhost.png'),
	(2, '/img/catGhost.png'),
	(3, '/img/defaultGhost1.png'),
	(4, '/img/defaultGhost2.png'),
	(5, '/img/sangwooGhost.png'),
	(6, '/img/rabbitGhost.png'),
	(7, '/img/musicGhost.png'),
	(8, '/img/alienGhost.png'),
	(9, '/img/glassesGhost.png');

-- 테이블 oomool.feed 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `content` text DEFAULT NULL,
  `author` int(11) DEFAULT NULL,
  `room_question_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn1b0bk4s6rsfvxr8r9runlt5h` (`author`),
  KEY `FKh0t9tu0k1ltv12ovm7639ycis` (`room_question_id`),
  CONSTRAINT `FKh0t9tu0k1ltv12ovm7639ycis` FOREIGN KEY (`room_question_id`) REFERENCES `game_room_question` (`id`),
  CONSTRAINT `FKn1b0bk4s6rsfvxr8r9runlt5h` FOREIGN KEY (`author`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 oomool.feed_image 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `original_name` varchar(255) DEFAULT NULL,
  `save_folder` varchar(255) DEFAULT NULL,
  `save_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `feed_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ucsld0tx762qhq8sp5khgv3n` (`feed_id`),
  CONSTRAINT `FK6ucsld0tx762qhq8sp5khgv3n` FOREIGN KEY (`feed_id`) REFERENCES `feed` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 oomool.game_room 구조 내보내기
CREATE TABLE IF NOT EXISTS `game_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `end_date` date NOT NULL,
  `question_type` tinyint(4) NOT NULL CHECK (`question_type` between 0 and 1),
  `uid` varchar(10) DEFAULT NULL,
  `start_date` date NOT NULL,
  `title` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 oomool.game_room_question 구조 내보내기
CREATE TABLE IF NOT EXISTS `game_room_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `seq` int(11) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpj973p97gk8pq3mmcumbxowe9` (`question_id`),
  KEY `FK4v1e45xrfvi9ta1fk5iiktb31` (`room_id`),
  CONSTRAINT `FK4v1e45xrfvi9ta1fk5iiktb31` FOREIGN KEY (`room_id`) REFERENCES `game_room` (`id`),
  CONSTRAINT `FKpj973p97gk8pq3mmcumbxowe9` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 oomool.notification 구조 내보내기
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `body` varchar(255) DEFAULT NULL,
  `read_at` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` enum('SYSTEM','WRITE_FEED') DEFAULT NULL,
  `game_room_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsg6hhgfhf8vbw1o0ulargc185` (`game_room_id`),
  KEY `FKb0yvoep4h4k92ipon31wmdf7e` (`user_id`),
  CONSTRAINT `FKb0yvoep4h4k92ipon31wmdf7e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKsg6hhgfhf8vbw1o0ulargc185` FOREIGN KEY (`game_room_id`) REFERENCES `game_room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 oomool.player 구조 내보내기
CREATE TABLE IF NOT EXISTS `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar_color` varchar(255) NOT NULL,
  `guess` bit(1) DEFAULT NULL,
  `manitti_id` int(11) DEFAULT NULL,
  `nickname` varchar(255) NOT NULL,
  `player_type` enum('MASTER','PLAYER') NOT NULL,
  `avatar_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh60774km7iptait4s4xxn4847` (`avatar_id`),
  KEY `FKq4wtxuumgs5400d3nx3pt4nkk` (`room_id`),
  KEY `FKj57d4kgk8qu7i73lu7xsbq8fb` (`user_id`),
  CONSTRAINT `FKh60774km7iptait4s4xxn4847` FOREIGN KEY (`avatar_id`) REFERENCES `avatar` (`id`),
  CONSTRAINT `FKj57d4kgk8qu7i73lu7xsbq8fb` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKq4wtxuumgs5400d3nx3pt4nkk` FOREIGN KEY (`room_id`) REFERENCES `game_room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 oomool.push_notification_token 구조 내보내기
CREATE TABLE IF NOT EXISTS `push_notification_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `token` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK392dtjhu7q3o30w3dhbh095ik` (`user_id`),
  CONSTRAINT `FK392dtjhu7q3o30w3dhbh095ik` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 oomool.question 구조 내보내기
CREATE TABLE IF NOT EXISTS `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL,
  `question` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 oomool.question:~67 rows (대략적) 내보내기
DELETE FROM `question`;
INSERT INTO `question` (`id`, `level`, `question`) VALUES
	(1, 1, '내 마니띠의 첫인상을 말해주세요!'),
	(2, 1, '내 마니띠에게 힘이 되어줄 명언을 보내주세요!'),
	(3, 1, '마니띠의 일상 생활에서 느껴지는 에너지나 분위기는 어떤가요?'),
	(4, 1, '마니띠가 좋아할만한 음악 장르나 곡은 무엇인가요?'),
	(5, 1, '마니띠가 좋아할만한 활동이나 취미가 무엇인가요?'),
	(6, 1, '마니띠가 좋아할만한 영화 장르나 영화 캐릭터는 무엇일 것 같아요?'),
	(7, 1, '마니띠의 방은 어떻게 꾸며져 있을 것 같나요?'),
	(8, 1, '마니띠는 새로운 사람들과 쉽게 친해지는 것 같나요?'),
	(9, 1, '마니띠의 "오늘"은 어떤 하루였을 것 같나요?'),
	(10, 1, '내 마니띠가 유튜버라면 무슨 컨텐츠가 어울릴 것 같나요?'),
	(11, 1, '마니띠는 친구들이랑 무엇을 할 때 가장 좋아하는 것 같나요?'),
	(12, 1, '마니띠랑 닮았다고 생각하는 동물이나 인물이 있다면 무엇인가요?'),
	(13, 2, '마니띠의 인생 여행지는 어디인 것 같나요?'),
	(14, 2, '마니띠의 가장 친한 친구에 대해 이야기 해주세요!'),
	(15, 2, '마니띠가 특별히 좋아하는 가수나 밴드가 있나요?'),
	(16, 2, '마니띠와 처음 대화를 나누었던 날, 어떤 이야기를 주고 받았나요?'),
	(17, 2, '마니띠와 가고싶은 나만의 맛집은 어디인가요?'),
	(18, 2, '마니띠가 좋아하는 활동이나 취미가 무엇인가요?'),
	(19, 2, '약속 시간에 내가 도착하지 못했다! 내 마니띠의 예상되는 반응은?!'),
	(20, 2, '나와 마니띠의 닮은 점이 있다면 무엇인가요?'),
	(21, 2, '요즘 마니띠의 최대 관심사는?'),
	(22, 2, '내 마니또에게 추천해주고 싶은 헤어스타일은 무엇인가요?'),
	(23, 3, '마니띠의 매력 중 어떤 부분이 사람들에게 임팩트를 줄 것 같나요?'),
	(24, 3, '요즘 내 마니띠의 최대 관심사는 무엇일까요?'),
	(25, 3, '마니띠의 소확행(소소하고 확실한 행복)을 찾아주세요!'),
	(26, 3, '내 마니띠가 자주 하는 말버릇이 있다면 어떤 것인가요?'),
	(27, 3, '마니띠와 같이 가고 싶은 장소를 말해주세요!'),
	(28, 3, '가장 최근에 마니띠와 함께 했던 것은 무엇인가요?'),
	(29, 3, '내 마니띠가 가장 예쁘거나 잘생겨 보였던 순간은 언제인가요?'),
	(30, 3, '우리의 관계를 5글자로 소개한다면 _ _ _ _ _ 이다!'),
	(31, 3, '내 마니띠의 얼굴 중 가장 마음에 드는 부분은 어디인가요?'),
	(32, 3, '마니띠와 같이 숙소 생활을 하게 된다면 어떨 것 같나요?'),
	(33, 3, '마니띠를 한 마디로 정의한다면 _ _ 이다.'),
	(34, 3, '마니띠를 만화나 애니메이션의 주인공으로 상상해본다면 어떤 캐릭터일 것 같아요?'),
	(35, 3, '마니띠와 함께 가보고 싶은 여행 장소가 있다면 어디인가요?'),
	(36, 4, '마니띠의 미래에 대해 상상해보면 어떤 모습일 것 같나요?'),
	(37, 4, '마니띠가 특별히 소중히 여기는 가치나 신념이 무엇인가요?'),
	(38, 4, '마니띠에게 감동 혹은 진심으로 고마웠던 에피소드가 있나요?'),
	(39, 4, '마니띠에게 배우고 싶은 점이나 닮고 싶은 점이 있다면 무엇인가요?'),
	(40, 4, '마니띠와 마음을 나누고 싶은 주제나 이야기가 있나요?'),
	(41, 4, '나만 알고있는 마니띠의 재능이 있다면?'),
	(42, 4, '우리의 우정에 있어서 내 마니띠가 가장 중요하게 생각하는 것은 무엇인가요?'),
	(43, 4, '이런건 달라도 너무 다르다! 마니띠와 나와 안맞는 부분이 있다면?'),
	(44, 4, '스트레스를 많이 받은 나의 마니띠! 무엇을 하고 있나요?'),
	(45, 4, '내가 기분이 안좋아 보인다면, 내 마니띠의 행동은?!'),
	(46, 4, '내 마니띠의 노래방 18번은?!'),
	(47, 4, '우리가 처음 만난 과거로 돌아간다면, 하고 싶은 말은 무엇인가요?'),
	(48, 4, '마니띠와 보낸 시간 중 가장 웃겼던 에피소드는 무엇인가요?'),
	(49, 4, '마니띠에 대해 최근 알게 된 새로운 사실이 있다면 무엇인가요?'),
	(50, 4, '이것만은 고집있다! 꺾지 못하는 내 마니띠의 똥고집은?!'),
	(51, 4, '내 마니띠가 이것만은 정말 잘한다! 자랑해주세요!'),
	(52, 4, '내 마니띠가 유독 싫어하는 음식은 무엇인가요?'),
	(53, 4, '내 마니띠랑 친해지게 된 계기가 무엇이였나요?'),
	(54, 5, '마니띠에게 말하지 못했던 서운한 감정을 털어보아요!'),
	(55, 5, '마니띠에게 평소 이야기하고 싶었지만 하지 못했던 진심이 있나요?'),
	(56, 5, '내 마니띠가 고쳐줬으면 하는 점은 무엇인가요?'),
	(57, 5, '내 마니띠의 첫인상과 현재 내가 생각하고 있는 인상을 적어주세요!'),
	(58, 5, '내 마니띠가 제일 좋아하는 것은 무엇인가요?'),
	(59, 5, '마니띠에게 진심으로 감동받았던 순간은?'),
	(60, 5, '내 마니띠와 가장 기억에 남는 추억은 무엇인가요?'),
	(61, 5, '올해가 가기전, 마니띠와 꼭 함께 이루고 싶은 목표가 있나요?'),
	(62, 5, '마니띠에게 진심으로 미안했지만, 제대로 사과하지 못했던 일이 있나요?'),
	(63, 5, '내 마니띠와 즐거웠던 흑역사가 있다면?'),
	(64, 5, '마니띠를 만나고 내게 생긴 변화를 공유해주세요!'),
	(65, 5, '내 마니띠에게 의지가 되었던 순간이 있었나요?'),
	(66, 5, '내 마니띠에게 해주고 싶은 조언은?'),
	(67, 5, '내가 보는 너에게 이것만은 꼭 말해주고 싶다!');

-- 테이블 oomool.social_login 구조 내보내기
CREATE TABLE IF NOT EXISTS `social_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider` varchar(255) NOT NULL,
  `provider_id` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf17poggafa7bx8g6ya5pkkt58` (`user_id`),
  CONSTRAINT `FKf17poggafa7bx8g6ya5pkkt58` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 oomool.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL CHECK (`role` between 0 and 1),
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
