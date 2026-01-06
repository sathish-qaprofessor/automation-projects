/**
 * Convert string to camelCase format
 * @param {string} str - String to convert (e.g., "Platform Portability")
 * @returns {string} - CamelCase string (e.g., "platformPortability")
 */
export const toCamelCase = (str) => {
    return str
        .toLowerCase()
        .split(' ')
        .map((word, index) => {
            if (index === 0) {
                return word;
            }
            return word.charAt(0).toUpperCase() + word.slice(1);
        })
        .join('');
};
