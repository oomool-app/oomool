// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  css: ['@/assets/css/global.scss', '@/assets/css/tailwind.scss'],
  modules: [
    '@nuxtjs/eslint-module',
    '@nuxtjs/tailwindcss',
    '@nuxtjs/stylelint-module',
    'shadcn-nuxt',
    '@vite-pwa/nuxt',
    '@pinia/nuxt',
    'nuxt-vuefire',
  ],
  plugins: ['@/plugins/messages.client.ts', '@/plugins/api.ts'],
  shadcn: {
    /**
     * Prefix for all the imported component
     */
    prefix: '',
    /**
     * Directory that the component lives in.
     * @default "./components/ui"
     */
    componentDir: './components/ui',
  },
  runtimeConfig: {
    public: {
      oomoolApiUrl: process.env.NUXT_PUBLIC_OOMOOL_API_URL,
      oomoolSiteUrl: process.env.NUXT_PUBLIC_OOMOOL_SITE_URL,
      vapidKey: process.env.NUXT_PUBLIC_FIREBASE_FCM_VAPID_KEY,
    },
  },
  app: {
    head: {
      link: [
        {
          rel: 'stylesheet',
          type: 'text/css',
          href: 'https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css',
        },
      ],
    },
  },
  pwa: {
    workbox: {
      runtimeCaching: [
        {
          // 이미지 캐싱 설정
          urlPattern: /\.(?:png|jpg|jpeg|svg)$/,
          handler: 'CacheFirst',
          options: {
            cacheName: 'images',
            expiration: {
              maxEntries: 10,
              maxAgeSeconds: 24 * 60 * 60 * 30, // 30 days
            },
          },
        },
      ],
    },
    strategies: 'injectManifest',
    injectRegister: null,
    registerType: 'autoUpdate',
    devOptions: {
      enabled: true,
      type: 'module',
      navigateFallbackAllowlist: [/^\/$/],
    },
    filename: 'firebase-messaging-sw.js',
    injectManifest: {
      rollupFormat: 'iife',
    },
    manifest: {
      name: '우물',
      short_name: '우물',
      description: '우리들의 물음표',
      lang: 'ko',
      display: 'standalone',
      background_color: '#61339B',
      theme_color: '#61339B',
      start_url: '/',
      icons: [
        {
          src: '/android-chrome-192x192.png',
          sizes: '192x192',
          type: 'image/png',
        },
        {
          src: '/android-chrome-512x512.png',
          sizes: '512x512',
          type: 'image/png',
        },
      ],
    },
  },
  vuefire: {
    config: {
      apiKey: process.env.FIREBASE_API_KEY,
      authDomain: process.env.FIREBASE_AUTH_DOMAIN,
      projectId: process.env.FIREBASE_PROJECT_ID,
      storageBucket: process.env.FIREBASE_STORAGE_BUCKET,
      messagingSenderId: process.env.FIREBASE_MESSAGING_SENDER_ID,
      appId: process.env.FIREBASE_APP_ID,
      measurementId: process.env.FIREBASE_MEASUREMENT_ID,
    },
  },
});
