pipeline {
    agent any

    environment {
        DOCKER_IMAGE_FULL_NAME = ''
    }

    parameters {
        string(name: 'DOCKER_IMAGE_NAME', defaultValue: '', description: 'Docker镜像名，空为项目名', trim: true)
        string(name: 'DOCKER_IMAGE_TAG', defaultValue: '', description: 'Docker镜像Tag，空为项目版本号', trim: true)
    }

    stages {
        stage('Prepare') {
            steps {
                // 准备 docker 构建与部署的相关信息
                String dockerImageName = "${DOCKER_IMAGE_NAME}"
                String dockerImageTag = "${DOCKER_IMAGE_TAG}"
                // 获取 Docker 镜像 短名
                if (!dockerImageName) {
                    dockerImageName = sh(
                            script: "./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout",
                            returnStdout: true
                    ).trim()
                }
                // 获取 Docker 镜像 tag
                if (!dockerImageTag) {
                    dockerImageTag = sh(
                            script: "./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout",
                            returnStdout: true
                    ).trim()
                }
                // 生成 Docker 镜像 全名
                DOCKER_IMAGE_FULL_NAME = "echcz/${dockerImageName}:${dockerImageTag}"
            }
        }
        stage('Build: Jar') {
            steps {
                sh './mvnw -DskipTests=true clean package'
            }
        }
        stage('Build: Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE_FULL_NAME} ."
            }
        }
        stage('Deploy: Docker run') {
            steps {
                sh "docker run -d -p 80:80 ${DOCKER_IMAGE_FULL_NAME}"
            }
        }
    }
}