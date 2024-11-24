const withFamilyPermission = (config) => {
  if (!config.ios) {
    config.ios = {};
  }
  if (!config.ios.infoPlist) {
    config.ios.infoPlist = {};
  }

  // Add a description for motion permissions (if required by your app)
  config.ios.infoPlist["NSMotionUsageDescription"] =
    "This app needs access to motion data for step tracking.";

  // Add Family Controls entitlements (required for Family Controls)
  if (!config.ios.entitlements) {
    config.ios.entitlements = {};
  }

  config.ios.entitlements["com.apple.developer.family-controls"] = true;

  return config;
};

module.exports = withFamilyPermission;