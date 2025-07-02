INSERT INTO public.course (course_id,semester_code,subject_code,teacher_id) VALUES
	 (1,'SU25','SWP','346925a3-c98a-4688-871a-4a0520924013'),
	 (2,'SU25','SWT','346925a3-c98a-4688-871a-4a0520924013'),
	 (3,'SU25','PRF','346925a3-c98a-4688-871a-4a0520924013'),
	 (4,'SU25','PRJ','346925a3-c98a-4688-871a-4a0520924013');
INSERT INTO public.room (room_id,room_number) VALUES
	 (1,'AL-R301'),
	 (2,'DE-310'),
	 (3,'AL-L103');
INSERT INTO public.semester (semester_code,description,end_date,start_date) VALUES
	 ('FA2024',NULL,'2024-12-31','2024-07-01'),
	 ('SP2024',NULL,'2024-06-30','2024-01-01'),
	 ('FA2025',NULL,'2025-12-31','2025-09-01'),
	 ('SP25',NULL,'2025-04-30','2025-01-01'),
	 ('SU25',NULL,'2025-08-31','2025-05-01');
INSERT INTO public.slot (slot_id,end_time,start_time) VALUES
	 (1,'09:00:00','07:30:00'),
	 (2,'10:40:00','09:10:00');
INSERT INTO public.subject (subject_code,status,subject_name) VALUES
	 ('SWP',true,'software project'),
	 ('SWT',true,'software testing'),
	 ('PRF',true,'programming'),
	 ('PRJ',true,'java web');
INSERT INTO public.users (id,dob,email,full_name,phone_number,user_id,class_code) VALUES
	 ('9de1438b-74a4-4094-be3a-51df8bbd0b2d','2003-07-16','lamthon@gmail.com','Lam Thon',NULL,'e2b76ce2-3ca4-49c7-84ce-45a2879881d1',NULL),
	 ('346925a3-c98a-4688-871a-4a0520924013',NULL,'teacher@gmail','teacher',NULL,'4db1a867-d423-4236-a39a-46057696c6b0',NULL),
	 ('0790bcb9-b167-4c69-9435-099aa535d161',NULL,'email0@gmail.com','user0',NULL,'22ba4f5e-f339-4075-9420-28f8376b9427',NULL),
	 ('28b88186-b4b7-429b-be9e-c3131721f8f9',NULL,'email1@gmail.com','user1',NULL,'8096bcd9-f149-44da-bf67-182210ff8351',NULL),
	 ('b60d965c-37db-465c-98d4-e03d7c2897f8',NULL,'email2@gmail.com','user2',NULL,'ba6bbf83-a336-4e41-bdc3-1745291e9db8',NULL),
	 ('c0e5186c-ec9d-45bb-a856-72b0a0467979',NULL,'email3@gmail.com','user3',NULL,'41e1f516-a2ae-45c7-8348-3d2471561890',NULL),
	 ('fe9baa19-b3ac-4e4b-8816-d681fb8fe496',NULL,'email4@gmail.com','user4',NULL,'dbc96a71-54c1-4c57-bee9-18fa79eb4c2d',NULL),
	 ('dee073e1-5200-4040-a4f5-e092e800bf7b',NULL,'email5@gmail.com','user5',NULL,'fb4fb576-15f8-45f6-8a8e-da00309a8329',NULL),
	 ('9dd90c4c-d500-4ab7-87f2-4bf67928a414',NULL,'email6@gmail.com','user6',NULL,'54069114-9109-43da-953b-33d6cecf03b0',NULL),
	 ('c5b6635e-2da7-45f3-8e42-74f81f8fdb83',NULL,'email7@gmail.com','user7',NULL,'30fb22d0-14d5-4a5e-89be-5ae2a97926b8',NULL);
INSERT INTO public.users (id,dob,email,full_name,phone_number,user_id,class_code) VALUES
	 ('8f7d874b-1680-4851-bb95-e683e463391d',NULL,'email8@gmail.com','user8',NULL,'a81b0788-febc-4713-9554-e78fcc26f7de',NULL),
	 ('b8229152-388c-4128-98ef-60396f700df4',NULL,'email9@gmail.com','user9',NULL,'bacab13b-1b1d-4c35-8b38-e15fb2ce3967',NULL),
	 ('3119f660-a11a-4507-8171-c6a040d14c93',NULL,'email10@gmail.com','user10',NULL,'9a2932dd-15b4-49cf-89c0-10cc8b22c19e',NULL),
	 ('38fbb9b0-aab7-4308-95de-8d79b2394503',NULL,'email11@gmail.com','user11',NULL,'ab612533-436b-47d0-8351-497ddb2f746e',NULL),
	 ('34b2b210-2a27-4f8a-860f-cca0466ab8b5',NULL,'email12@gmail.com','user12',NULL,'cd93e7c9-3c4e-409f-98ea-35cb091b5566',NULL),
	 ('7d086779-b4a9-47d5-8b49-2bda961ba4f9',NULL,'email13@gmail.com','user13',NULL,'12c90bed-d7d5-4a2c-a7f5-9801eda7e135',NULL),
	 ('269188bd-b18d-4240-befb-d2e5a35e6a05',NULL,'email14@gmail.com','user14',NULL,'512b87b3-4507-44fc-8c89-fe1451cd5ad0',NULL),
	 ('4387831e-d432-4090-be31-774fe41fe856',NULL,'email15@gmail.com','user15',NULL,'5b80dfff-8fc5-4bd0-b5a8-355c5a9825df',NULL),
	 ('63a456d5-2f5d-42b2-a175-3c955a3ea60b',NULL,'email16@gmail.com','user16',NULL,'f8e2b263-1dd0-4ded-9c8e-97e74afc3ec7',NULL),
	 ('560e08f9-6a2a-49a0-a436-e407fbf837e3',NULL,'email17@gmail.com','user17',NULL,'f0b3cfcd-a9ae-44f9-9080-73a124a501b9',NULL);
INSERT INTO public.users (id,dob,email,full_name,phone_number,user_id,class_code) VALUES
	 ('593e7f41-b37d-4026-b30c-dfcf2aeab60f',NULL,'email18@gmail.com','user18',NULL,'dc8cc152-6ff9-414c-b293-ad04d8122471',NULL),
	 ('1ef0e370-c61a-4261-96a8-acb7a2d35524',NULL,'email19@gmail.com','user19',NULL,'89f3b4ae-9669-4758-9c21-3cdf820fdaf0',NULL);
