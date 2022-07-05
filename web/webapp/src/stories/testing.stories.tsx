import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ComponentStory, ComponentMeta } from '@storybook/react';
import { TestChart } from '../testingchart';

export default {
  title: 'Component/New',
  component: TestChart,
} as ComponentMeta<typeof TestChart>;

const Template: ComponentStory<typeof TestChart> = (args) => (
  <TestChart />
);

export const HundredPages = Template.bind({});
