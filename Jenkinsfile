@Library(['general-pipeline']) _

node('master') {
    withSlack channel: 'jenkins', {
        timeout(45) {
            stage('init') {
                deleteDir()
                git url: 'git@github.com:emartech/android-emarsys-sdk.git', branch: 'master'
            }

            lock(env.ANDROID_EMARSYS_SDK_BUILD) {
                stage('core') {
                    build job: 'android-core-sdk'
                }

                stage('mobile-engage') {
                    build job: 'android-mobile-engage-sdk'
                }

                stage('predict') {
                    build job: 'android-predict-sdk'
                }

                stage('emarsys') {
                    build job: 'android-emarsys-sdk'
                }

                stage('sample') {
                    build job: 'android-emarsys-sdk-sample'
                }
            }
        }
    }
}
