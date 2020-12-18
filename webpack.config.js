var path = require("path");

module.exports = {
  entry: {bundle: "./src/main/js/app.js", survey: "./src/main/js/survey.js"},
  devtool: "sourcemaps",
  cache: true,
  mode: "development",
  output: {
    path: __dirname + "/target/classes/static/built",
    filename: '[name].js',
  },
  module: {
    rules: [
      {
        test: path.join(__dirname, "."),
        exclude: /(node_modules)/,
        use: [
          {
            loader: "babel-loader",
            options: {
              presets: ["@babel/preset-env", "@babel/preset-react"],
            },
          },
        ],
      },
      {
        test: /\.css$/i,
        use: ["style-loader", "css-loader"],
      },
    ],
  },
};
