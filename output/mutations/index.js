const fs = require('fs');
const path = require('path');

module.exports.createGarden = fs.readFileSync(path.join(__dirname, 'createGarden.gql'), 'utf8');
module.exports.updateGarden = fs.readFileSync(path.join(__dirname, 'updateGarden.gql'), 'utf8');
