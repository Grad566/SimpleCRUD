FROM gradle:8.7.0-jdk21

WORKDIR /

COPY / .

RUN gradle installBootDist

CMD ./build/install/todo-boot/bin/todo --spring.profiles.active=dev