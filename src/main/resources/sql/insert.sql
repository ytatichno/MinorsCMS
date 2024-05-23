insert into companies (address, description, name, phone, photo, shortname)
values
    ('Санкт-Петербург, Невский пр-кт, 128', 'Крупный банк','Банк Санкт-Петербург', null, null, 'БСПб'),
    ('San Francisco, 5 avenue, 1', 'Large company, that produce lots of soft','Microsoft', null, null, 'MS'),
    ('Москва, Ленинские горы, 12к7', 'Один из крупнейших суперкомпьютерных центрова','Научно-исследовательский вычислительный центр при Московском государственном университете им. Ломоносова', null, null, 'НИВЦ МГУ'),
    ('Москва, Карсноармейская, 12', 'Один из институтов Российской Академии Наук','Институт Системного Программирования им. Иванникова Российской Академии Наук', null, null, 'ИСП РАН'),
    ('Москва, Ленинский горы, 12к1', 'Факультет МГУ','Факультет Вычислистельной математики и кибернетики Московского государственного университета им. Ломоносова', null, null, 'ВМК МГУ');


insert into courses ( description, name, company_id)
values
    ('Курс про то, как настраивать авторизацию, проверять пароли, хранить секретные данные',
     'Безопасный Backend', (SELECT id FROM companies WHERE shortname='БСПб')),
    ('Про то, как собирать и анализировать данные, связанные с финансами',
     'Финансовая аналитика', (SELECT id FROM companies WHERE shortname='БСПб')),

    ('MS SQL is powerful and robust DBMS, in course you will be taught how to write effective queries',
     'MS SQL', (SELECT id FROM companies WHERE shortname='MS')),
    ('Short tour on Azure platform',
     'Azure', (SELECT id FROM companies WHERE shortname='MS')),
    ('By the end of the course you will write your own VSCode Extension',
     'VSCode engineering', (SELECT id FROM companies WHERE shortname='MS')),
    ('MS Word is powerful text editor. If you want to write your coursework, you can rely on MS Word',
     'Coursework in Word', (SELECT id FROM companies WHERE shortname='MS')),

    ('На этом спецкурсе разбираются темы, которые в основной курс не помещаются',
     'Актуальные задачи современного ФА и МФ', (SELECT id FROM companies WHERE shortname='ВМК МГУ')),
    ('Rust многие называют убийцей C++',
     'Язык Rust', (SELECT id FROM companies WHERE shortname='ВМК МГУ'));


insert into teachers (education, fathername, lastname, mail, name, company_id)
values
    ('ВМК МГУ', null, 'Петров', 'petrov@cs.msu.ru', 'Иван', (SELECT id FROM companies WHERE shortname = 'ВМК МГУ')),
    ('ВМК МГУ', null, 'Капустин', 'cabbage@mail.ru', 'Николай', (SELECT id FROM companies WHERE shortname = 'ВМК МГУ')),
    ('ВМК МГУ', null, 'Чернов', 'blackav@cs.msu.ru', 'Александр', (SELECT id FROM companies WHERE shortname = 'ВМК МГУ')),

    ('БСПб', null, 'Денских', 'savaden@b.spb.ru', 'Савелий', (SELECT id FROM companies WHERE shortname = 'БСПб')),

    ('MS', null, 'Colins', 'jcol@onenote.com', 'Jim', (SELECT id FROM companies WHERE shortname = 'MS')),
    ('MS', null, 'Brown', 'tonybony@onenote.com', 'Tony', (SELECT id FROM companies WHERE shortname = 'MS')),
    ('MS', null, 'Levinski', 'levinski.steve@onenote.com', 'Steve', (SELECT id FROM companies WHERE shortname = 'MS'));

insert into teachers_courses ( course_id, teacher_id)
values
    ((SELECT id FROM courses WHERE name = 'Актуальные задачи современного ФА и МФ'),
     (SELECT id FROM teachers WHERE lastname = 'Капустин')
     ),
    ((SELECT id FROM courses WHERE name = 'Язык Rust'),
     (SELECT id FROM teachers WHERE lastname = 'Чернов')
     ),
    ((SELECT id FROM courses WHERE name = 'Язык Rust'),
     (SELECT id FROM teachers WHERE lastname = 'Петров')
     ),

    ((SELECT id FROM courses WHERE name = 'Безопасный Backend'),
     (SELECT id FROM teachers WHERE lastname = 'Денских')
     ),
    ((SELECT id FROM courses WHERE name = 'Финансовая аналитика'),
     (SELECT id FROM teachers WHERE lastname = 'Денских')
     ),

    ((SELECT id FROM courses WHERE name = 'Coursework in Word'),
     (SELECT id FROM teachers WHERE lastname = 'Colins')
     ),
    ((SELECT id FROM courses WHERE name = 'MS SQL'),
     (SELECT id FROM teachers WHERE lastname = 'Colins')
     ),
    ((SELECT id FROM courses WHERE name = 'VSCode engineering'),
     (SELECT id FROM teachers WHERE lastname = 'Brown')
     ),
    ((SELECT id FROM courses WHERE name = 'Azure'),
     (SELECT id FROM teachers WHERE lastname = 'Brown')
     ),
    ((SELECT id FROM courses WHERE name = 'Azure'),
     (SELECT id FROM teachers WHERE lastname = 'Levinski')
     ),
    ((SELECT id FROM courses WHERE name = 'MS SQL'),
     (SELECT id FROM teachers WHERE lastname = 'Levinski')
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
    ((SELECT id FROM courses WHERE name='Язык Rust'),(SELECT id FROM students WHERE lastname='Межуев')),
    ((SELECT id FROM courses WHERE name='Язык Rust'),(SELECT id FROM students WHERE lastname='Митрофанов')),
    ((SELECT id FROM courses WHERE name='Язык Rust'),(SELECT id FROM students WHERE lastname='Опрышко')),
    ((SELECT id FROM courses WHERE name='Актуальные задачи современного ФА и МФ'),(SELECT id FROM students WHERE lastname='Латыпова')),
    ((SELECT id FROM courses WHERE name='Актуальные задачи современного ФА и МФ'),(SELECT id FROM students WHERE lastname='Межуев')),
    ((SELECT id FROM courses WHERE name='Актуальные задачи современного ФА и МФ'),(SELECT id FROM students WHERE lastname='Опрышко')),
    ((SELECT id FROM courses WHERE name='Актуальные задачи современного ФА и МФ'),(SELECT id FROM students WHERE lastname='Митрофанов')),
    ((SELECT id FROM courses WHERE name='Azure'),(SELECT id FROM students WHERE lastname='Дударенко')),
    ((SELECT id FROM courses WHERE name='Финансовая аналитика'),(SELECT id FROM students WHERE lastname='Иванова')),
    ((SELECT id FROM courses WHERE name='Финансовая аналитика'),(SELECT id FROM students WHERE lastname='Митрофанов')),
    ((SELECT id FROM courses WHERE name='MS SQL'),(SELECT id FROM students WHERE lastname='Дударенко')),
    ((SELECT id FROM courses WHERE name='MS SQL'),(SELECT id FROM students WHERE lastname='Латыпова'));



insert into schedule (classroom, day_of_week, end_time, start_time, teach_cour)
values
    ('523', 'Понедельник', '10:20', '8:45',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT id FROM teachers WHERE lastname='Чернов')
        AND
          course_id = (SELECT id FROM courses WHERE name='Язык Rust')
      )
    ),
    ('523', 'Среда', '10:20', '8:45',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT id FROM teachers WHERE lastname='Петров')
        AND
          course_id = (SELECT id FROM courses WHERE name='Язык Rust')
      )
    ),

    ('П-8А', 'Вторник', '17:50', '16:20',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT id FROM teachers WHERE lastname='Капустин')
        AND
          course_id = (SELECT id FROM courses WHERE name='Актуальные задачи современного ФА и МФ')
      )
    ),

    ('П-8А', 'Понедельник', '17:50', '16:20',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT id FROM teachers WHERE lastname='Levinski')
        AND
          course_id = (SELECT id FROM courses WHERE name='Azure')
      )
    ),
    ('П-8А', 'Четверг', '17:50', '16:20',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT id FROM teachers WHERE lastname='Levinski')
        AND
          course_id = (SELECT id FROM courses WHERE name='Azure')
      )
    ),

    ('604', 'Четверг', '10:30', '12:05',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT id FROM teachers WHERE lastname='Levinski')
        AND
          course_id = (SELECT id FROM courses WHERE name='MS SQL')
      )
    ),

    ('П-14', 'Четверг', '10:30', '12:05',
     (SELECT teach_cour_id
      FROM teachers_courses
      WHERE
          teacher_id = (SELECT id FROM teachers WHERE lastname='Денских')
        AND
          course_id = (SELECT id FROM courses WHERE name='Финансовая Аналитика')
      )
    );
