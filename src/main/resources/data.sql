INSERT INTO `personaje` VALUES (1,'','65','secundario','cdn-img','jose','80'),(2,'\0','45','principal','cdn-img','lucas','59'),(3,'\0','33','secundario','cdn-img','robertito','92'),(4,'\0','85','terciario','cdn-img','romario','60'),(5,'\0','65','terciario','cdn-img','fulanito','99'),(6,'\0','98','cuaternario','cdn-img','menganito','70'),(7,'','60','quintenario','cdn-img','rogelio','65');
INSERT INTO `peliculaserie` VALUES (1,'9','2001-10-12','cdn-img','matrix'),(2,'7','1980-12-11','cnd-img','alien'),(3,'8','1989-10-19','cdn-img','robocop'),(4,'7','1995-11-28','cdn-img','rambo');
INSERT INTO `rel_peliculas_personajes` VALUES (1,2),(3,2),(4,2),(1,3),(2,3),(3,4),(4,4),(1,5),(1,6);
INSERT INTO `genero` VALUES (1,'cdn-img','accion'),(2,'cdn-img','drama'),(3,'cdn-img','terror'),(4,'cdn-img','comedia'),(5,'cdn-img','romance'),(6,'cdn-img','policial');
INSERT INTO `rel_peliculas_generos` VALUES (1,1),(1,2),(2,3),(3,1),(3,6),(4,1);
INSERT INTO `usuario` VALUES (1,null,null,null,'$2a$10$wokCtLGyNIY5NGyyUZj19.WhrTv.HHhljyTXT9jOpxg9rOY3E/G3.','ADMIN','admin');
INSERT INTO `usuario` VALUES (2,null,null,null,'$2a$10$djVM/hzl7er0QrwrmHpLwuXzBxTN65el94.6BRDh2HsFVgB0fU5bm','USER','user');