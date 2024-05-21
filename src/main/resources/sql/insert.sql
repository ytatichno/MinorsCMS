insert into companies (address, description, name, phone, photo, short_name)
values
    ('Санкт-Петербург, Невский пр-кт, 128', 'Крупный банк','Банк Санкт-Петербург', null, null, 'БСПб'),
    ('San Francisco, 5 avenue, 1', 'Large company, that produce lots of soft','Microsoft', null, null, 'MS'),
    ('Москва, Ленинские горы, 12к7', 'Один из крупнейших суперкомпьютерных центрова','Научно-исследовательский вычислительный центр при Московском государственном университете им. Ломоносова', null, null, 'НИВЦ МГУ'),
    ('Москва, Карсноармейская, 12', 'Один из институтов Российской Академии Наук','Институт Системного Программирования им. Иванникова Российской Академии Наук', null, null, 'ИСП РАН'),
    ('Москва, Ленинский горы, 12к1', 'Факультет МГУ','Факультет Вычислистельной математики и кибернетики Московского государственного университета им. Ломоносова', null, null, 'ВМК МГУ');


insert into courses ( description, name, company_id)
values
    ('Курс про то, как настраивать авторизацию, проверять пароли, хранить секретные данные',
     'Безопасный Backend', (SELECT company_id FROM companies WHERE short_name='БСПб')),
    ('Про то, как собирать и анализировать данные, связанные с финансами',
     'Финансовая аналитика', (SELECT company_id FROM companies WHERE short_name='БСПб')),

    ('MS SQL is powerful and robust DBMS, in course you will be taught how to write effective queries',
     'MS SQL', (SELECT company_id FROM companies WHERE short_name='MS')),
    ('Short tour on Azure platform',
     'Azure', (SELECT company_id FROM companies WHERE short_name='MS')),
    ('By the end of the course you will write your own VSCode Extension',
     'VSCode engineering', (SELECT company_id FROM companies WHERE short_name='MS')),
    ('MS Word is powerful text editor. If you want to write your coursework, you can rely on MS Word',
     'Coursework in Word', (SELECT company_id FROM companies WHERE short_name='MS')),

    ('На этом спецкурсе разбираются темы, которые в основной курс не помещаются',
     'Актуальные задачи современного ФА и МФ', (SELECT company_id FROM companies WHERE short_name='ВМК МГУ')),
    ('Rust многие называют убийцей C++',
     'Язык Rust', (SELECT company_id FROM companies WHERE short_name='ВМК МГУ'));


insert into teachers (education, fathername, lastname, mail, name, company_id)
values
    ('ВМК МГУ', null, 'Петров', 'petrov@cs.msu.ru', 'Иван', (SELECT company_id FROM companies WHERE short_name = 'ВМК МГУ')),
    ('ВМК МГУ', null, 'Капустин', 'cabbage@mail.ru', 'Николай', (SELECT company_id FROM companies WHERE short_name = 'ВМК МГУ')),
    ('ВМК МГУ', null, 'Чернов', 'blackav@cs.msu.ru', 'Александр', (SELECT company_id FROM companies WHERE short_name = 'ВМК МГУ')),

    ('БСПб', null, 'Денских', 'savaden@b.spb.ru', 'Савелий', (SELECT company_id FROM companies WHERE short_name = 'БСПб')),

    ('MS', null, 'Jim', 'jcol@onenote.com', 'Colins', (SELECT company_id FROM companies WHERE short_name = 'MS')),
    ('MS', null, 'Tony', 'tonybony@onenote.com', 'Brown', (SELECT company_id FROM companies WHERE short_name = 'MS')),
    ('MS', null, 'Steve', 'levinski.steve@onenote.com', 'Levinski', (SELECT company_id FROM companies WHERE short_name = 'MS'));

insert into teachers_courses ( course_id, teacher_id)
values
    ((SELECT course_id FROM courses WHERE name = 'Актуальные задачи современного ФА и МФ'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Капустин')
     ),
    ((SELECT course_id FROM courses WHERE name = 'Язык Rust'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Чернов')
     ),
    ((SELECT course_id FROM courses WHERE name = 'Язык Rust'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Петров')
     ),

    ((SELECT course_id FROM courses WHERE name = 'Безопасный Backend'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Денских')
     ),
    ((SELECT course_id FROM courses WHERE name = 'Финансовая аналитика'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Денских')
     ),

    ((SELECT course_id FROM courses WHERE name = 'Coursework in Word'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Colins')
     ),
    ((SELECT course_id FROM courses WHERE name = 'MS SQL'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Colins')
     ),
    ((SELECT course_id FROM courses WHERE name = 'VSCode engineering'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Brown')
     ),
    ((SELECT course_id FROM courses WHERE name = 'Azure'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Brown')
     ),
    ((SELECT course_id FROM courses WHERE name = 'Azure'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Levinski')
     ),
    ((SELECT course_id FROM courses WHERE name = 'MS SQL'),
     (SELECT teacher_id FROM teachers WHERE lastname = 'Levinski')
     );

insert into students (lastname, name, phone)
values
    ('Дударенко','Денис', '+79811917702'),
    ('Иванова','Анастасия', '+79811917703'),
    ('Латыпова','Аделина', '+79811917704'),
    ('Межуев','Тимофей', '+79811917705'),
    ('Митрофанов','Андрей', '+79811917706'),
    ('Опрышко','Мария', '+79811917707');

insert into students_courses (course_id, student_id)
values
    ((SELECT course_id FROM courses WHERE name='Язык Rust'),(SELECT id FROM students WHERE lastname='Межуев')),
    ((SELECT course_id FROM courses WHERE name='Язык Rust'),(SELECT id FROM students WHERE lastname='Митрофанов')),
    ((SELECT course_id FROM courses WHERE name='Язык Rust'),(SELECT id FROM students WHERE lastname='Опрышко')),
    ((SELECT course_id FROM courses WHERE name='Актуальные задачи современного ФА и МФ'),(SELECT id FROM students WHERE lastname='Латыпова')),
    ((SELECT course_id FROM courses WHERE name='Актуальные задачи современного ФА и МФ'),(SELECT id FROM students WHERE lastname='Межуев')),
    ((SELECT course_id FROM courses WHERE name='Актуальные задачи современного ФА и МФ'),(SELECT id FROM students WHERE lastname='Опрышко')),
    ((SELECT course_id FROM courses WHERE name='Актуальные задачи современного ФА и МФ'),(SELECT id FROM students WHERE lastname='Митрофанов')),
    ((SELECT course_id FROM courses WHERE name='Azure'),(SELECT id FROM students WHERE lastname='Дударенко')),
    ((SELECT course_id FROM courses WHERE name='Финансовая аналитика'),(SELECT id FROM students WHERE lastname='Иванова')),
    ((SELECT course_id FROM courses WHERE name='Финансовая аналитика'),(SELECT id FROM students WHERE lastname='Митрофанов')),
    ((SELECT course_id FROM courses WHERE name='MS SQL'),(SELECT id FROM students WHERE lastname='Дударенко')),
    ((SELECT course_id FROM courses WHERE name='MS SQL'),(SELECT id FROM students WHERE lastname='Латыпова'));



insert into schedule (classroom, day_of_week, end_time, start_time, teach_cour)
values
    ('523', 'Понедельник', '10:20', '8:45',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT teacher_id FROM teachers WHERE lastname='Чернов')
        AND
          course_id = (SELECT course_id FROM courses WHERE name='Язык Rust')
      )
    ),
    ('523', 'Среда', '10:20', '8:45',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT teacher_id FROM teachers WHERE lastname='Петров')
        AND
          course_id = (SELECT course_id FROM courses WHERE name='Язык Rust')
      )
    ),

    ('П-8А', 'Вторник', '17:50', '16:20',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT teacher_id FROM teachers WHERE lastname='Капустин')
        AND
          course_id = (SELECT course_id FROM courses WHERE name='Актуальные задачи современного ФА и МФ')
      )
    ),

    ('П-8А', 'Понедельник', '17:50', '16:20',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT teacher_id FROM teachers WHERE lastname='Levinski')
        AND
          course_id = (SELECT course_id FROM courses WHERE name='Azure')
      )
    ),
    ('П-8А', 'Четверг', '17:50', '16:20',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT teacher_id FROM teachers WHERE lastname='Levinski')
        AND
          course_id = (SELECT course_id FROM courses WHERE name='Azure')
      )
    ),

    ('604', 'Четверг', '10:30', '12:05',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT teacher_id FROM teachers WHERE lastname='Levinski')
        AND
          course_id = (SELECT course_id FROM courses WHERE name='MS SQL')
      )
    ),

    ('П-14', 'Четверг', '10:30', '12:05',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT teacher_id FROM teachers WHERE lastname='Денских')
        AND
          course_id = (SELECT course_id FROM courses WHERE name='Финансовая Аналитика')
      )
    );
