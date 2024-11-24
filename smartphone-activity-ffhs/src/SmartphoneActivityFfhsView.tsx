import { requireNativeView } from 'expo';
import * as React from 'react';
import { ViewProps } from "react-native";

interface SmartphoneActivityFfhsViewProps extends ViewProps {}

const NativeView: React.ComponentType =
  requireNativeView('SmartphoneActivityFfhs');

export default function SmartphoneActivityFfhsView(props: SmartphoneActivityFfhsViewProps) {
  return <NativeView {...props} />;
}
