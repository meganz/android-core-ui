/**
 * This script is the CI pipeline for CoreUI libraries.
 */

@Library('jenkins-android-shared-lib') _

pipeline {
    agent { label 'mac-jenkins-slave-android || mac-jenkins-slave' }
    options {
        // Stop the build early in case of compile or test failures
        skipStagesAfterUnstable()
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '1'))
        timeout(time: 1, unit: 'HOURS')
        gitLabConnection('GitLabConnection')
    }
    environment {
        LC_ALL = "en_US.UTF-8"
        LANG = "en_US.UTF-8"

        JAVA_HOME = "/opt/buildtools/zulu21.40.17-ca-jdk21.0.6-macosx"
        ANDROID_HOME = "/opt/buildtools/android-sdk"

        // Jenkins build log will be saved in this file.
        CONSOLE_LOG_FILE = "console.txt"
    }
    post {
        failure {
            script {
                comment = ":x: Android CoreUI Build Failed! (Build: ${env.BUILD_NUMBER})" +
                        "\nTitle:   ${env.CHANGE_TITLE}" +
                        "\nBranch:   ${env.GIT_BRANCH}" +
                        "\nAuthor:   ${env.CHANGE_AUTHOR_DISPLAY_NAME}" +
                        "\nCommit:   ${env.GIT_COMMIT}" +
                        "\nCommit Msg:   ${env.GITLAB_OA_LAST_COMMIT_MESSAGE}" +
                        "\nLink:   ${env.CHANGE_URL}"

                util.downloadJenkinsConsoleLog(CONSOLE_LOG_FILE)
                String jenkinsLogLink = util.uploadFileToGitLab(
                        CONSOLE_LOG_FILE,
                        util.getGitLabProjectId(env.GIT_URL)
                )

                if (util.hasGitLabMergeRequest()) {
                    def failureMessage = failureMessage() +
                            "<br/>Build Log: ${jenkinsLogLink}"
                    util.sendToMR(
                            failureMessage,
                            util.getGitLabProjectId(env.GIT_URL)
                    )
                }

                slackSend color: 'danger', message: comment
                slackUploadFile filePath: 'console.txt', initialComment: "Android CoreUI build Log \nLink:${env.CHANGE_URL}"
            }
        }
        success {
            script {
                comment = ":white_check_mark: Android CoreUI Build Successful!(Build: ${env.BUILD_NUMBER}) " +
                        "\nTitle:   ${env.CHANGE_TITLE} " +
                        "\nBranch:   ${env.GIT_BRANCH} " +
                        "\nAuthor:   ${env.CHANGE_AUTHOR_DISPLAY_NAME} " +
                        "\nCommit:   ${env.GIT_COMMIT}" +
                        "\nCommit Msg:   ${env.GITLAB_OA_LAST_COMMIT_MESSAGE}" +
                        "\nLink:   ${env.CHANGE_URL}"

                if (util.hasGitLabMergeRequest()) {
                    String successMessage = ":white_check_mark: Build Succeeded!(Build: ${env.BUILD_NUMBER})\n\n" +
                            "**Last Commit:** (${env.GIT_COMMIT})" +
                            util.getLastCommitMessage()

                    util.sendToMR(
                            successMessage,
                            util.getGitLabProjectId(env.GIT_URL)
                    )
                }

                slackSend color: "good", message: comment
            }
        }
        cleanup {
            cleanWs(cleanWhenFailure: true)
        }
    }
    stages {
        stage('Prepare') {
            steps {
                gitlabCommitStatus(name: 'Preparation') {
                    script {
                        util.printEnv()
                    }
                }
            }
        }
        stage('Verify Unit Tests') {
            steps {
                gitlabCommitStatus(name: 'Unit Test') {
                    script {
                        println("Run All Unit Tests")
                        sh "./gradlew testDebugUnitTest"
                    }
                }
            }
        }
        stage('Verify Build') {
            steps {
                gitlabCommitStatus(name: 'Build') {
                    script {
                        println("Build Debug")
                        sh "./gradlew assembleDebug"
                    }
                }
            }
        }
    }
}

private String failureMessage() {
    return ":x: Build Failed(Build: ${env.BUILD_NUMBER})" +
            "<br/>Last Commit Message: ${util.getLastCommitMessage()}" +
            "Last Commit ID: ${env.GIT_COMMIT}"
}

