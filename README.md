<h1>Данный сервис осуществляет унификацию списка пользователей и их email-ов</h1>

Список вида:<br/>
user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru<br/>
user2 -> foo@gmail.com, ups@pisem.net<br/>
user3 -> xyz@pisem.net, vasya@pupkin.com<br/>
user4 -> ups@pisem.net, aaa@bbb.ru<br/>
user5 -> xyz@pisem.net<br/>

Приводится к списку<br/> 
user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru<br/>
user3 -> xyz@pisem.net, vasya@pupkin.com<br/>

Для сбора приложения следует использовать команду в корне проекта<br/>
<b>mvn package</b>

После выполнения сборки в директории <b>/target</b> создастся файл <b>UserMailListChecker-1.0.jar</b>

Запуск программы осуществляется командой <b>java -jar UserMailListChecker-1.0.jar</b>

После запуска программа ожидает подачу данных на вход по формату указанному в строке приветствия.
Окончания ввода данных осуществляется вводом пустой строки.

В случае если все строки переданные на вход некорректного формата - программа выдаст сообщение об ошибке и прекратит 
работу.
Если часть строк некорректны они будут проигнорированы.
Лишние или недостающие пробелы между разделителями (-> и ,) будут удалены (или добавлены).

Примечание: для валидации email-ов используется библиотека commons-validator 