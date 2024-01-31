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
  ],
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
      meta: [
        {
          name: 'apple-mobile-web-app-capable',
          content: 'yes',
        },
        {
          name: 'apple-mobile-web-app-status-bar-style',
          content: 'black-translucent',
        },
      ],
    },
  },
  pwa: {
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
          src: '/img/icons/icon-48x48.png',
          sizes: '48x48',
          type: 'image/png',
          purpose: 'any',
        },
        {
          src: '/img/icons/icon-96x96.png',
          sizes: '96x96',
          type: 'image/png',
          purpose: 'any',
        },
        {
          src: '/img/icons/icon-128x128.png',
          sizes: '128x128',
          type: 'image/png',
          purpose: 'any',
        },
        {
          src: '/img/icons/icon-192x192.png',
          sizes: '192x192',
          type: 'image/png',
          purpose: 'any',
        },
        {
          src: '/img/icons/icon-384x384.png',
          sizes: '384x384',
          type: 'image/png',
          purpose: 'any',
        },
        {
          src: '/img/icons/icon-512x512.png',
          sizes: '512x512',
          type: 'image/png',
          purpose: 'any',
        },
      ],
    },
  },
});
