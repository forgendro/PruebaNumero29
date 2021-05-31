#!groovy​

@Library('utils') _


//COMMON PARAMETERS FOR EACH ENVIRONMENT
class CommonParams {
    static String playbook='/etc/ansible/roles/ansible-dev.yml'
}

//DEVELOP ENVIRONMENT PARAMETERS
class DevParams {

	static String target1='infra01'
    static String host1='http://micros-infra01.rtictx.org'
	static String target2='infra02'
    static String host2='http://micros-infra02.rtictx.org'
    static String microPath='/opt/aplicaciones/config-server'
    static String perfil='git'
    static int puerto=51010
    static String urleureka1='http://micros-infra01.rtictx.org:51000/eureka/' 
    static String urleureka2='http://micros-infra02.rtictx.org:51000/eureka/' 
    static String repository='http://micros-ic02.rtictx.org:8081/repository/maven-snapshots/'
    static String healthPath='/actuator/health'
}

//PRODUCCION ENVIRONMENT PARAMETERS
class ProdParams {


    
}
 
pipeline {
    agent any
    options { skipDefaultCheckout() }
    environment {
        PATH = "$PATH:/usr/local/bin"
        GIT_URL =  "${gitlabSourceRepoHttpUrl}"
		GIT_BRANCH = "${gitlabSourceBranch}"
    }
	
    stages {

        stage('Checkout') {
            steps {
                cleanWs()			
                script {
                    log.box("Comienzo checkout ${GIT_URL} rama: ${GIT_BRANCH}")
                    repository.checkout(GIT_URL, GIT_BRANCH)
                    repository.checkUser()
                    ARTIFACTID = readMavenPom().getArtifactId()
                    VERSIONID = readMavenPom().getVersion()
                    jar = "${ARTIFACTID}-${VERSIONID}"
                    grupo = readMavenPom().getGroupId() 
                }
            }
        }

        stage('Unit Tests') {  
            steps {
                script {
                    log.box('Comienzo ejecucion test unitarios')
                    test.junit();     
                }
            }
        }

        stage('Static Analysis') {
            steps {
               // withSonarQubeEnv('Sonar'){
                    script {
                        log.box('Comienzo ejecucion analisis de codigo')
                     //   test.sonar();   
                    }   
               // }           
            }
        }

        stage('Build Snapshot') {
			when { expression { "${params.branch}" != 'master' } }
            steps {  
                script {
                    log.box('Comienzo construccion snapshot')
                    build.snapshot();   
                }    
            }
        }

        stage ('Build Release') {
            when { expression { "${params.branch}" == 'master' } }
            steps {        
                script {
                    log.box('Comienzo construccion release')
                    build.release();   
                }
            }
        }

        stage('Deploy desarrollo') {
            when { expression { params.branch != 'master' } }
            steps {
                deploy(DevParams.target1, DevParams.host1, CommonParams, DevParams, VERSIONID, ARTIFACTID, jar, grupo)
                deploy(DevParams.target2, DevParams.host2, CommonParams, DevParams, VERSIONID, ARTIFACTID, jar, grupo)
            }
        }
        
        stage('Deploy Produccion') {
            when {expression { params.branch == 'master' }}
            steps {
                deploy(ProdParams.target1, ProdParams.host1, CommonParams, ProdParams, VERSIONID.getAt(0..VERSIONID.length() - 10), ARTIFACTID, ARTIFACTID + "-" + VERSIONID.getAt(0..VERSIONID.length() - 10), grupo)
                deploy(ProdParams.target2, ProdParams.host2, CommonParams, ProdParams, VERSIONID.getAt(0..VERSIONID.length() - 10), ARTIFACTID, ARTIFACTID + "-" + VERSIONID.getAt(0..VERSIONID.length() - 10), grupo)
            }
        } 

        stage('Analyze microservice') {
            when {expression { params.branch == 'master' }}
            steps {
                def repository_name = GIT_URL.replace("/", "%2F");
                sh ("curl --location --request PUT 'http://localhost:3000/api/microservices/update' --header 'Content-Type: application/json' --data-raw '{\"repo\":{ \"url\": \"${GIT_URL}\", \"name\": \"${repository_name}\"},\"lang\": \"1\",\"branch\": \"${GIT_BRANCH}\",\"id_environment\": 1000}'")
            }
        } 

       stage('Analyze code') {
            steps {  
                script {
                    String[] url = GIT_URL.split('/')
                    String repo = url[url.length -1]
                    repo = repo.substring(0, repo.length() -4)
                    sh ("curl --location --request PUT 'http://micros-infra02.rtictx.org:3000/api/microservices/update' --header 'Content-Type: application/json' --data-raw '{\"repo\":{ \"url\": \"${GIT_URL}\", \"name\": \"${repo}\"},\"lang\": \"1\",\"branch\": \"${GIT_BRANCH}\",\"id_environment\": 1000}'")
                }
            }
        }
                  
                   


    } //Stages

    post ('Notifications') {
        always   { 
        //    email (ARTIFACTID, VERSIONID, 'Despliegue')
        //    slack (ARTIFACTID, VERSIONID, 'Despliegue')
            cleanWs()
        }
    }

} //Pipeline