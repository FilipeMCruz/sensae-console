const fs = require('fs');
const path = require('path');

module.exports.data = fs.readFileSync(path.join(__dirname, 'data.gql'), 'utf8');
