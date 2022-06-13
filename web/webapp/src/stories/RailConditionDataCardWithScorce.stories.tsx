import 'bootstrap/dist/css/bootstrap.min.css';
import { ComponentStory, ComponentMeta } from '@storybook/react';
import { RailConditionDataCardWithScorce } from '../common/RailConditionDataCardWithScorce';

export default {
  title: 'Component/Card2',
  component: RailConditionDataCardWithScorce,
} as ComponentMeta<typeof RailConditionDataCardWithScorce>;

const Template: ComponentStory<typeof RailConditionDataCardWithScorce> = (
  args
) => <RailConditionDataCardWithScorce {...args} />;

export const HundredPages = Template.bind({});
