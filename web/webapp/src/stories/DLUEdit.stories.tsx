import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ComponentStory, ComponentMeta } from '@storybook/react';
import { DataLookUpEdit } from '../DataLookUp/DataLookUpComponent/DataLookUpEdit';
import '../i18n';

export default {
  title: 'Component/DLUEdit',
  component: DataLookUpEdit,
} as ComponentMeta<typeof DataLookUpEdit>;

const Template: ComponentStory<typeof DataLookUpEdit> = (args) => (
  <DataLookUpEdit {...args} />
);

export const HundredPages = Template.bind({});
