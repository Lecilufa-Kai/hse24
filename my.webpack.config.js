const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const path = require('path');

module.exports = {
  entry: __dirname + '/src/main/resources/static/tsx/index.tsx',
  output: {
    filename: 'main.js',
    chunkFilename: '[name].[contentHash].chunk.crm-csp.js',
    path: path.normalize(__dirname + '/src/main/resources/static/js'),
  },
  resolve: {
    // Add '.ts' and '.tsx' as resolvable extensions.
    extensions: ['.ts', '.tsx', '.js', '.json'],
  },
  module: {
    noParse: [/\.test\.ts?x$/],
    rules: [
      // All files with a '.ts' or '.tsx' extension will be handled by 'ts-loader'.
      {
        test: /\.tsx?$/,
        loader: 'ts-loader',
      }
    ],
  },

  plugins: [
    new CleanWebpackPlugin(),
  ],
};
