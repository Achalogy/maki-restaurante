FROM ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    ca-certificates \
    git \
    gnupg \
    build-essential \
    openssh-client \
    openjdk-21-jdk \
  && rm -rf /var/lib/apt/lists/*

# Instalar Google Chrome (para pruebas de Selenium)
RUN curl -fsSL https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg \
  && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
  && apt-get update && apt-get install -y --no-install-recommends \
    google-chrome-stable \
  && rm -rf /var/lib/apt/lists/*

RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
  && apt-get install -y nodejs \
  && rm -rf /var/lib/apt/lists/*

RUN npm install -g yarn pnpm

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# RUN mkdir -p /etc/docker && \
#     echo '{"insecure-registries": ["192.168.0.218:5000"]}' > /etc/docker/daemon.json

# COPY ./install-docker.sh .
# RUN chmod +x ./install-docker.sh && ./install-docker.sh && rm ./install-docker.sh

WORKDIR /workspace

CMD ["bash"]
