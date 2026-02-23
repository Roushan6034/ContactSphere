export default {
    darkMode: 'selector',
    content: [
        "./src/main/resources/templates/**/*.html",
        "./src/**/*.{js,ts,jsx,tsx}",
        "./node_modules/flowbite/**/*.js"
    ],
    theme: {
        extend: {
            colors: {
                brand: '#1C64F2',
                heading: '#111827',
                'neutral-tertiary': '#f3f4f6',
                'fg-brand': '#1C64F2'
            }
        },
    },
    plugins: [
        require('flowbite/plugin')
    ],
}