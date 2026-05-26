FROM ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive
ENV CI=true

# Creamos una configuración temporal para ignorar las firmas GPG y permitir repositorios inseguros
RUN echo "Acquire::AllowInsecureRepositories \"true\";" > /etc/apt/apt.conf.d/99allow-insecure && \
    echo "Acquire::AllowDowngradeToInsecureRepositories \"true\";" >> /etc/apt/apt.conf.d/99allow-insecure && \
    echo "APT::Get::AllowUnauthenticated \"true\";" >> /etc/apt/apt.conf.d/99allow-insecure

# Ejecutamos update permitiendo errores de firma y luego instalamos con --allow-unauthenticated
RUN apt-get update || true

RUN apt-get install -y --allow-unauthenticated --no-install-recommends \
    curl \
    ca-certificates \
    git \
    gnupg \
    build-essential \
    openssh-client \
    openjdk-21-jdk \
  && rm -rf /var/lib/apt/lists/*

RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
  && apt-get install -y --allow-unauthenticated nodejs \
  && rm -rf /var/lib/apt/lists/*

RUN npm install -g yarn pnpm http-server

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

WORKDIR /workspace

COPY angular ./angular
COPY springboot ./springboot

RUN cd angular && pnpm install

RUN chmod +x springboot/mvnw

EXPOSE 4200 8080

CMD ["sh", "-c", "cd /workspace/springboot && ./mvnw spring-boot:run & cd /workspace/angular && pnpm build && http-server dist/angular -p 4200 --spa"]
