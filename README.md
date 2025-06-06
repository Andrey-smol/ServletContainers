# Домашнее задание к занятию «2.1. Servlet Containers»

В качестве решения пришлите ссылки на ваши GitHub-проекты в личном кабинете студента на сайте [netology.ru](https://netology.ru).

**Важная информация**

1. Перед стартом работы изучите, пожалуйста, ссылки на главной странице [репозитория с домашними заданиями](../README.md).
2. Если у вас что-то не получилось, тогда оформляйте Issue [по установленным правилам](../report-requirements.md).

## Как сдавать задачи

1. Создайте на вашем компьютере Maven-проект.
1. Инициализируйте в нём пустой Git-репозиторий.
1. Добавьте в него готовый файл [.gitignore](../.gitignore).
1. Добавьте в этот же каталог остальные необходимые файлы.
1. Сделайте необходимые коммиты.
1. Создайте публичный репозиторий на GitHub и свяжите свой локальный репозиторий с удалённым.
1. Сделайте пуш: удостоверьтесь, что ваш код появился на GitHub.
1. Ссылку на ваш проект отправьте в личном кабинете на сайте [netology.ru](https://netology.ru).

## CRUD

### Легенда

В рамках лекции мы реализовали практически полноценный In-Memory CRUD-сервер (Create Read Update Delete) на базе сервлетов. Этому серверу не хватает двух вещей:

1. Привести код в должный вид: вынести методы в константы, убрать дублирующийся код.
1. Реализовать репозиторий — пока вместо репозитория установлена заглушка.

### Задача

1. Осуществите рефакторинг кода.
1. Реализуйте репозиторий с учётом того, что методы репозитория могут вызываться конкурентно, т. е. в разных потоках.

Как должен работать `save`:

1. Если от клиента приходит пост с id=0, значит, это создание нового поста. Вы сохраняете его в списке и присваиваете ему новый id. Достаточно хранить счётчик с целым числом и увеличивать на 1 при создании каждого нового поста.
1. Если от клиента приходит пост с id !=0, значит, это сохранение (обновление) существующего поста. Вы ищете его в списке по id и обновляете. Продумайте самостоятельно, что вы будете делать, если поста с таким id не оказалось: здесь могут быть разные стратегии.

### Результат

В качестве решения пришлите ссылку на ваш GitHub-репозиторий в личном кабинете студента на сайте [netology.ru](https://netology.ru).

## WebApp Runner* (задача со звёздочкой)

Это необязательная задача, её выполнение не влияет на получение зачёта.

### Легенда

Не всегда удобно «таскать» за собой полноценный Tomcat: скачивать его, распаковывать и т. д. Достаточно часто используют библиотеку [WebApp Runner](https://github.com/heroku/webapp-runner), ранее (com.github.jsimone webapp-runner).

Встраивание WebApp Runner в ваш проект позволяет запускать его таким образом: `java -jar target/dependency/webapp-runner.jar target/<appname>.war`. Это достаточно удобно для размещения на облачных платформах.

### Задача

Добавьте в свою сборку скачивание `webapp-runner` согласно [инструкции](https://github.com/heroku/webapp-runner#using-with-maven-in-your-project).

Убедитесь, что сборка проходит, и ваш war-файл действительно запускается указанной выше командой.

### Результат

Реализуйте новую функциональность в ветке `feature/webapp-runner` вашего репозитория из предыдущего домашнего задания и откройте Pull Request.

В качестве результата пришлите ссылку на ваш Pull Request на GitHub в личном кабинете студента на сайте [netology.ru](https://netology.ru).

Для встраивания `webapp-runner` в проект надо внести изменения в `pom.xml`.
```xml
<plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>3.3.0</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>copy</goal>
                        </goals>
                        <configuration>
                            <artifactItems>
                                <artifactItem>
                                    <groupId>com.heroku</groupId>
                                    <artifactId>webapp-runner-main</artifactId>
                                    <version>${webapp-runner.version}</version>
                                    <destFileName>webapp-runner.jar</destFileName>
                                </artifactItem>
                            </artifactItems>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

```
Также надо уменьшить версию `jdk` до `jdk-19` в `pom.xml`
```xml
        <maven.compiler.target>19</maven.compiler.target>
        <maven.compiler.source>19</maven.compiler.source>
```
и в структуре проекта.

## DI

### Легенда

В рамках лекции мы посмотрели, как использовать Spring для связывания зависимостей.

Возникает вопрос, почему бы не использовать его в вашем приложении с сервлетами и не заменить указанный ниже код на DI со Spring:
```java
@Override
public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
}
```

### Задача

Замените код в методе `init` на DI со Spring с использованием методов конфигурирования бинов:

1. Annotation Config — ветка `feature/di-annotation`.
1. Java Config — ветка `feature/di-java`.

