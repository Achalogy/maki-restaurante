/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts,css}",
  ],
  theme: {
    extend: {
      colors: {
        "maki-orange": "#eb7c21",
        "maki-white": "#fefced",
        "maki-black": "#1b1b26",
        "maki-beige": "#f6ceaa",
        "maki-bg": "#fefced"
      },
      fontFamily: {
        "staatliches": ["Staatliches", "sans-serif"],
        "spinnaker": ["Spinnaker", "sans-serif"],
      }
    },
  },
  plugins: [],
}