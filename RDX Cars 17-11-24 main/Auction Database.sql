create database supercar_auction;
use supercar_auction;

CREATE TABLE cars (
car_id int primary key auto_increment,
car_model_name varchar(100),
car_model_year int,
car_location varchar(50),
car_starting_price varchar(50)
);
select * from cars;

insert into cars
values 
(101,"MXT INTERNATIONAL",2008,"Stuttgart, Germany","$22000"),
(102,"Rolls Royce Wraith Black Badge",2022,"Dubai, United Arab Emirates","$400000"),
(103,"Aston Martin DB4 GT Continuation",2018,"Zug, Switzerland","$90000"),
(104,"Mercedes-AMG GT Black Series",2021," Los Angeles, USA","$50000"),
(105,"Ferrari 488 GTB 70th Anniversary",2017,"Stuttgart, Germany","$45000"),
(106,"Mercedes-Benz S 680 Maybach",2022,"Stuttgart, Germany","$150000"),
(107,"Lamborghini Huracan EVO Coupe",2022,"North York, Ontario, Canada","$180000"),
(108,"Aston Martin DBS 770 Ultimate",2022,"Gersthofen, Bavaria, Germany","$220000"),
(109,"Bentley Continental GT Speed Edition 12",2024,"Charlotte, North Carolina, USA","$2800000"),
(110,"Genty Akylone Prototype",2021,"Dubai, United Arab Emirates","$32000"),
(111,"Ferrari 458 Italia",2012,"Bridgeport, Connecticut, USA","$186995"),
(112,"Porsche 911 (992) GT3 RS 'Weissach'",2024,"United Arab Emirates","$300000"),
(113,"Porsche 911 Turbo Coupe",1996,"Mount-Royal,Quebec,Canada","$400000"),
(114,"Lotus Exige 410 Sport 20th Anniversary",2021,"Milan, Italy","$30000"),
(115,"AVG BatMobil",2024,"Dubai, United Arab Emirates","$380000"),
(116,"Koenigsegg Jesko",2021," Munich, Germany","$39000000"),
(117,"McLaren 720S",2020,"London, United Kingdom","$29000000"),
(118,"Ford GT",2006,"Detroit, United States","$150000");


