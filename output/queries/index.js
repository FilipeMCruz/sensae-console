const fs = require('fs');
const path = require('path');

module.exports.history = fs.readFileSync(path.join(__dirname, 'history.gql'), 'utf8');
module.exports.fetchGardens = fs.readFileSync(path.join(__dirname, 'fetchGardens.gql'), 'utf8');
module.exports.fetchLatestData = fs.readFileSync(path.join(__dirname, 'fetchLatestData.gql'), 'utf8');
