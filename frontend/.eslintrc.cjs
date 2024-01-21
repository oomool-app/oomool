module.exports = {
  root: true,
  env: {
    browser: true,
    es2021: true,
    node: true,
  },
  parser: "vue-eslint-parser",
  parserOptions: {
    parser: "@typescript-eslint/parser",
  },
  extends: [
    "plugin:vue/vue3-recommended",
    "plugin:nuxt/recommended",
    "@nuxt/eslint-config",
  ],
  rules: {
    "vue/no-multiple-template-root": "off",
  },
};
