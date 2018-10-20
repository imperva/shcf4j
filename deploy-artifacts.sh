#!/usr/bin/env bash

if [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then
  openssl aes-256-cbc \
            -K $encrypted_2e308745a1f8_key \
            -iv $encrypted_2e308745a1f8_iv \
            -in ${TRAVIS_BUILD_DIR}/imperva.travis.gpg.enc \
            -out ${TRAVIS_BUILD_DIR}/imperva.travis.gpg \
            -d
  gradle uploadArchives \
            -PossrhUsername=${SONATYPE_USERNAME} \
            -PossrhPassword=${SONATYPE_PASSWORD} \
            -Psigning.keyId=${GPG_KEY_ID} \
            -Psigning.password=${GPG_KEY_PASSPHRASE} \
            -Psigning.secretKeyRingFile=${TRAVIS_BUILD_DIR}/imperva.travis.gpg
fi
