# 프론트엔드 포팅 매뉴얼

## 개발환경

- Node: 20.11.0
- Yarn: 1.22.21
- Nuxt.js: 3.9.3

## 사용한 외부 서비스

### Firebase FCM

PWA환경에서의 푸시 알림 구현을 위해 Firebase FCM을 사용하였습니다. 서비스 워커 등록을 위해 public/firebase-messaging-sw.js파일 내부에 게재되어 있는 FirebaseOptions을 서비스에 맞게 수정해야 합니다. 설정 방법은 [이 링크](https://firebase.google.com/docs/cloud-messaging/js/receive?hl=ko)로 대체합니다.

## Jenkins

### Credentials

서비스 운영에 필요한 민감 정보들을 수록합니다. `Jenkins 관리 → Credentials`에서 다음 정보들을 기록해야 합니다.

| ID                           | Kind        | Description                        |
| ---------------------------- | ----------- | ---------------------------------- |
| firebase-fcm-vapid-key       | Secret text | Firebase FCM 사용을 위한 VAPID Key |
| firebase-api-key             | Secret text | Firebase Api Key                   |
| firebase-messaging-sender-id | Secret text | Firebase Messaging Sender ID       |
| firebase-app-id              | Secret text | Firebase App ID                    |
| firebase-measurement-id      | Secret text | firebase Measurement ID            |

## Dotenv

본 프로젝트의 `frontend`폴더 안에는 `.env.example` 파일이 있습니다. 해당 파일에 `.env`파일에 수록되어야 할 민감 정보의 Key가 입력되어 있습니다. `.env`파일을 생성 후 `.env.example`파일에 기재된 Key에 대한 Value를 입력해야 합니다. 입력되어야 할 정보는 다음과 같습니다.

```toml
# 우물 API서버 URL 설정
NUXT_PUBLIC_OOMOOL_API_URL=

# 우물 사이트 URL 설정
NUXT_PUBLIC_OOMOOL_SITE_URL=

# Firebase FCM VAPID Key
NUXT_PUBLIC_FIREBASE_FCM_VAPID_KEY=

# Firebase Config
FIREBASE_API_KEY=
FIREBASE_AUTH_DOMAIN=
FIREBASE_PROJECT_ID=
FIREBASE_STORAGE_BUCKET=
FIREBASE_MESSAGING_SENDER_ID=
FIREBASE_APP_ID=
FIREBASE_MEASUREMENT_ID=
```

## Contributing

본 서비스는 한 레포지토리 안에 백엔드 환경과 프론트엔드 환경이 공존하는 형태로 이루어져 있습니다. 개발 컨벤션을 지키기 위해, 이 레포지토리의 루트에는 commit convention을 검사할 `commitlint`와 Java 환경에서의 `Checkstyle`을 체크하기 위한 `pre-commit`이 설정되어 있습니다. 아래에 본 기능을 활성화시키기 위한 방법을 수록합니다.

### Prerequisites

- Node v20.11.0: git pre-commit을 보다 간편하게 적용하기 위해 Node.js환경의 [husky](https://typicode.github.io/husky/)를 사용하였습니다. 따라서 본 과정을 수행하기 위해서는 Node가 필요합니다.
- Yarn v1.22.21: Yarn은 Node환경의 npm을 대체하는 Package Manager중 하나입니다. 본 프로젝트에서는 yarn을 기본 패키지 매니저로 사용하였기에 필요합니다.

### How to Install

1. root 폴더에서 다음 명령어를 실행합니다.

```bash
yarn install
```

1. frontend 폴더로 이동한 후 다음 명령어를 실행합니다.

```bash
yarn install
```

1. dev환경으로 테스트하고자 할 경우 다음 명령어를 실행합니다.

```bash
yarn dev
```
