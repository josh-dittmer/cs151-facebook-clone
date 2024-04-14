module.exports = {
    images: {
      remotePatterns: [
        {
          protocol: 'http',
          hostname: 'localhost',
          port: '9000',
          pathname: '/resource/**',
        },
      ],
    },
  }